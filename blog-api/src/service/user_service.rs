use crate::constants::common_constant;
use crate::error::AppError;
use crate::model::request::{LoginRequest, PasswordReq};
use crate::model::response::{MetaResp, RouterResp, UserInfoResp, UserMenuResp};
use crate::repo::menu_repo::MenuRepo;
use crate::repo::role_repo::RoleRepo;
use crate::repo::user_repo::UserRepo;
use crate::utils::jwt::generate_token;
use crate::utils::{hash_password, verify_password};
use std::sync::Arc;

pub struct UserService {
    user_repo: Arc<UserRepo>,
    menu_repo: Arc<MenuRepo>,
    role_repo: Arc<RoleRepo>,
}

impl UserService {
    pub fn new(pool: sqlx::PgPool) -> Self {
        UserService {
            user_repo: Arc::new(UserRepo::new(pool.clone())),
            menu_repo: Arc::new(MenuRepo::new(pool.clone())),
            role_repo: Arc::new(RoleRepo::new(pool)),
        }
    }

    pub async fn update_password(
        &self,
        user_id: i32,
        password_req: PasswordReq,
    ) -> Result<bool, AppError> {
        let user = self.user_repo.get_one_by_id(user_id).await?;
        if verify_password(&user.password, &password_req.old_password).is_ok() {
            let new_password = hash_password(&password_req.new_password)?;
            self.user_repo.update_password(new_password, user_id).await
        } else {
            Err(AppError::PasswordVerifyError)
        }
    }

    pub async fn login(&self, login_request: LoginRequest) -> Result<String, AppError> {
        let user = self
            .user_repo
            .get_one_by_username(&login_request.username)
            .await?;
        match verify_password(&user.password, &login_request.password) {
            Ok(_) => generate_token(user.id, 3)
                .map_err(|_| AppError::Internal("token生成失败!".to_string())),
            Err(_) => Err(AppError::PasswordVerifyError),
        }
    }

    pub async fn get_user_info(&self, user_id: i32) -> Result<UserInfoResp, AppError> {
        let avatar = self.user_repo.get_avatar(user_id).await?;
        let role_list = self.role_repo.get_role(user_id).await?;
        let permission_list = self
            .menu_repo
            .select_permission(user_id.to_string())
            .await?;
        Ok(UserInfoResp {
            id: user_id,
            avatar,
            role_list,
            permission_list,
        })
    }

    pub async fn get_user_menu(&self, user_id: i32) -> Result<Vec<RouterResp>, AppError> {
        let menu_list = self.menu_repo.select_menu(user_id).await?;
        Ok(Self::recur_routes(common_constant::PARENT_ID, &menu_list))
    }

    fn recur_routes(parent_id: i32, menu_list: &[UserMenuResp]) -> Vec<RouterResp> {
        menu_list
            .iter()
            .filter(|menu| menu.parent_id == parent_id)
            .map(|menu| {
                let mut route = RouterResp {
                    name: menu.menu_name.clone(),
                    path: Self::get_router_path(menu),
                    component: Self::get_component(menu),
                    meta: Some(MetaResp {
                        title: Some(menu.menu_name.clone()),
                        icon: menu.icon.clone(),
                        hidden: Some(menu.is_hidden),
                    }),
                    children: None,
                    always_show: None,
                    redirect: None,
                };
                if menu.menu_type == common_constant::TYPE_DIR {
                    let children = Self::recur_routes(menu.id, menu_list);
                    if !children.is_empty() {
                        route.always_show = Some(true);
                        route.redirect = Some("noRedirect".to_string());
                    }
                    route.children = Some(children);
                } else if Self::is_menu_frame(menu) {
                    route.meta = None;
                    let child = RouterResp {
                        name: menu.menu_name.clone(),
                        path: menu.path.clone().unwrap_or_default(),
                        component: menu.component.clone().unwrap_or_default(),
                        meta: Some(MetaResp {
                            title: Some(menu.menu_name.clone()),
                            icon: menu.icon.clone(),
                            hidden: Some(menu.is_hidden),
                        }),
                        children: None,
                        always_show: None,
                        redirect: None,
                    };
                    route.children = Some(vec![child]);
                }
                route
            })
            .collect()
    }

    fn get_router_path(menu: &UserMenuResp) -> String {
        let mut router_path = menu.path.clone().unwrap_or("".to_string());
        // 一级目录
        if menu.parent_id == common_constant::PARENT_ID
            && menu.menu_type == common_constant::TYPE_DIR
        {
            router_path = format!("/{}", menu.path.as_deref().unwrap_or(""));
        }
        // 一级菜单
        else if Self::is_menu_frame(menu) {
            router_path = "/".to_string();
        }
        router_path
    }

    fn is_menu_frame(menu: &UserMenuResp) -> bool {
        menu.parent_id == common_constant::PARENT_ID && common_constant::TYPE_MENU == menu.menu_type
    }

    fn get_component(menu: &UserMenuResp) -> String {
        let mut component = common_constant::LAYOUT.to_string();
        if menu.component.is_some() && !Self::is_menu_frame(menu) {
            component = menu.component.clone().unwrap();
        } else if menu.component.is_none() && Self::is_parent_view(menu) {
            component = common_constant::PARENT_VIEW.to_string();
        }
        component
    }

    fn is_parent_view(menu: &UserMenuResp) -> bool {
        menu.parent_id != common_constant::PARENT_ID && common_constant::TYPE_DIR == menu.menu_type
    }
}

use sqlx::PgPool;

pub struct TagRepo {
    pool: PgPool,
}

impl TagRepo {
    pub fn new(pool: PgPool) -> Self {
        TagRepo { pool }
    }
}

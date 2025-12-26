import {ElMessageBox, ElNotification} from "element-plus";

/**
 * 提示框
 * @param message 提示内容
 */
export const notifySuccess = (message: string) => {
    ElNotification({
        title: "成功",
        message,
        type: "success",
    });
};

/**
 * 确认框
 * @param content 提示内容
 */
export const messageConfirm = (content: string) => {
    return ElMessageBox.confirm(content, "系统提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        center: true,
        type: "warning",
    });
};

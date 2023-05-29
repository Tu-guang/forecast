import axios from './axios';

export const Login = async (datas) => {
    return await axios({
        // url: `/api/user/login`,
        url: `/api/user/login`,
        method: 'post',
        data: datas,
        headers:{
            "token": localStorage.getItem("token")
        }
    });
};

export const Logout = async () => {
    return await axios({
        url: `/api/user/logout`,
        method: 'post',
        headers:{
            "token": localStorage.getItem("token")
        }
    });
};

// 文件上传 api 函数简单封装
export const uploadApi = async ({file, onUploadProgress}) => {
    const formData = new FormData();
    formData.append('file', file);
    return await axios({
        url: '/api/file/upload',
        method: 'POST',
        data: formData,
        headers: {
            'Content-type': 'multipart/form-data',
            "token": localStorage.getItem("token")
        },
        onUploadProgress
    });
};
export const showImg = async () => {
    return await axios({
        url: '/api/file/image',
        method: 'POST',
        headers:{
            "token": localStorage.getItem("token")
        }
    });
};

export const Detect = async () => {
    return await axios({
        url: '/api/process/detect',
        method: 'POST',
        headers:{
            "token": localStorage.getItem("token")
        }
    });
};
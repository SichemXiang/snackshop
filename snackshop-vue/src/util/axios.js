// 引入axios
import axios from 'axios';
import { ElMessage } from "element-plus";
import router from '@/router';

let baseUrl="http://localhost:8080/";
// 创建axios实例
const httpService = axios.create({

  // baseURL:'http://localhost:8080/',
  // 请求超时时间

  timeout: 3000 // 需自定义
});

//添加请求和响应拦截器
// 添加请求拦截器
httpService.interceptors.request.use((config)=>{
  console.log('请求',config);
  const token = sessionStorage.getItem('token');
  if(token){
    config.headers['Authorization'] = token;
  }
  return config;
},(error)=>{
  console.log('请求异常',error);
  }
)

// 添加响应拦截器
httpService.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  return response;
}, function (error) {
  // 对响应错误做点什么
  if(error.response.status === 401 ){
    ElMessage.error("您未登录,请登录后操作");
    sessionStorage.clear();
    router.replace('/login');
  }else if (error.response.status === 400){
    ElMessage.error(error.response.data.message);
  }else if(error.response.status === 403){
    ElMessage.error("您未登录!请登录后操作");
  }else if (error.response.status === 500){
    ElMessage.error('后端异常-->'+error.response.data.message)
  }else {
    ElMessage.error('未知错误！');
  }
});

/*网络请求部分*/

/*
 *  get请求
 *  url:请求地址
 *  params:参数
 * */
export function get(url, params = {}) {
  return new Promise((resolve, reject) => {
    httpService({
      url: url,
      method: 'get',
      params: params
    }).then(response => {
      resolve(response);
    }).catch(error => {
      reject(error);
    });
  });
}

/*
 *  post请求
 *  url:请求地址
 *  params:参数
 * */
export function post(url, params = {}) {
  return new Promise((resolve, reject) => {
    httpService({
      url: url,
      method: 'post',
      data: params
    }).then(response => {
      console.log(response)
      resolve(response);
    }).catch(error => {
      console.log(error)
      reject(error);
    });
  });
}



/*
 *  delete请求
 *  url:请求地址
 * */
export function deleteData(url) {
  return new Promise((resolve, reject) => {
    httpService({
      url: url,
      method: 'delete'
    }).then(response => {
      console.log(response)
      resolve(response);
    }).catch(error => {
      console.log(error)
      reject(error);
    });
  });
}







/**
 * put方法，对应put请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function put(url, params) {
  return new Promise((resolve, reject) => {
    axios.put(url, params)
      .then(res => {
        resolve(res.data);
        // Loading.service(true).close();
        //  Message({message: '请求成功', type: 'success'});
      })
      .catch(err => {
        reject(err.data)
        // Loading.service(true).close();
        Message({message: '加载失败', type: 'error'});
      })
  });
}

/*
 *  文件上传
 *  url:请求地址
 *  params:参数
 * */
export function fileUpload(url, params = {}) {
  return new Promise((resolve, reject) => {
    httpService({
      url: url,
      method: 'post',
      data: params,
      headers: { 'Content-Type': 'multipart/form-data' }
    }).then(response => {
      resolve(response);
    }).catch(error => {
      reject(error);
    });
  });
}


export function getServerUrl(){
  return baseUrl;
}

export default {
  get,
  post,
  fileUpload,
  deleteData,
  put,
  getServerUrl
}
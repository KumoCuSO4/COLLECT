import axios from "axios";
import {TASK_MODULE} from "./_prefix";


/**
 * 发布众测任务
 * @param {*} payload
 * @returns
 */
 export const createTask = payload => {
    const {
      taskId,
      name,
      description,
      end_time,
      worker_num,
      type,
      other,
      user_id,
      user_name,
    } = payload;
    return axios
      .post(`${TASK_MODULE}/createTask`, {
      taskId,
      name,
      description,
      end_time,
      worker_num,
      type,
      other,
      user_id,
      user_name,
      })
      .then(res => {
        return res.data;
      });
  };



/**
 *  浏览任务广场全部任务
 * @param {*} payload
 * @returns
 */
export const getALL=payload=>{
    const{
        page
    }=payload
    return axios.get(`${TASK_MODULE}/getALL`,{
        page
    })
    .then(res=>{
        return res.data;
    })
}


/**
 * 众包工人浏览任务广场
 * @param {*} payload
 * @returns
 */
 export const getList=payload=>{
    const{
        userId,
        page
    }=payload
    return axios.get(`${TASK_MODULE}/getList`,{
        userId,
        page
    })
    .then(res=>{
        return res.data;
    })
}

/**
 * 浏览详细任务信息
 * @param {*} payload
 * @returns
 */
 export const getTaskInformation=payload=>{
    const{
        taskID
    }=payload
    return axios.get(`${TASK_MODULE}/{taskID}/getTaskInformation`,{
        taskID
    })
    .then(res=>{
        return res.data;
    })
}

/**
 * 上传可执行文件
 * @param {*} payload
 * @returns
 */
 export const uploadFile=payload=>{
    const{
        taskID
    }=payload
    return axios.post(`${TASK_MODULE}/{taskID}/uploadFile`,{
        taskID
    })
    .then(res=>{
        return res.data;
    })
}


/**
 * 上传文档
 * @param {*} payload
 * @returns
 */
 export const uploadDoc=payload=>{
    const{
        taskID
    }=payload
    return axios.post(`${TASK_MODULE}/{taskID}/uploadDoc`,{
        taskID
    })
    .then(res=>{
        return res.data;
    })
}


/**
 * 下载可执行文件
 * @param {*} payload
 * @returns
 */
 export const downloadFile=payload=>{
    const{
        taskId
    }=payload
    return axios.get(`${TASK_MODULE}/{taskID}/downloadFile`,{
        taskId
    })
    .then(res=>{
        return res.data;
    })
}

/**
 * 下载文档
 * @param {*} payload
 * @returns
 */
 export const downloadDoc=payload=>{
    const{
        taskId
    }=payload
    return axios.get(`${TASK_MODULE}/{taskID}/downloadDoc`,{
        taskId
    })
    .then(res=>{
        return res.data;
    })
}
 



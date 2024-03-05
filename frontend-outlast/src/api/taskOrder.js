import axios from "axios";
import {TASK_ORDER_MODULE} from "./_prefix";

/**
 * 工人接受任务
 * @param {*} payload
 * @returns
 */
 export const accptTask=payload=>{
    const{
        userId,
        username,
        taskname,
        taskID
    }=payload
    return axios.post(`${TASK_ORDER_MODULE}/{taskID}/getTaskInformation`,{
        userId,
        username,
        taskname,
        taskID
    })
    .then(res=>{
        return res.data;
    })
}

/**
 * 工人浏览正在进行的任务
 * @param {*} payload
 * @returns
 */
 export const getDoing=payload=>{
    const{
        userId,
        page
    }=payload
    return axios.get(`${TASK_ORDER_MODULE}/getDoing`,{
        userId,
        page
    })
    .then(res=>{
        return res.data;
    })
}

/**
 * 工人浏览已完成的任务
 * @param {*} payload
 * @returns
 */
 export const getFinished=payload=>{
    const{
        userId,
        page
    }=payload
    return axios.get(`${TASK_ORDER_MODULE}/getFinished`,{
        userId,
        page
    })
    .then(res=>{
        return res.data;
    })
}
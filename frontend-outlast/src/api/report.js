import axios from "axios";
import {REPORT_MODULE} from "./_prefix";


/**
 * 提交报告
 * @param {*} payload
 * @returns
 */
 export const submit=payload=>{
    const{
        user_id,
        user_name,
        task_id,
        task_name,
        description,
        step,
        equipment,
        post_time
    }=payload
    return axios.post(`${REPORT_MODULE}/submit`,{
        user_id,
        user_name,
        task_id,
        task_name,
        description,
        step,
        equipment,
        post_time
    })
    .then(res=>{
        return res.data;
    })
}



/**
 * 浏览报告列表
 * @param {*} payload
 * @returns
 */
 export const getList=payload=>{
    const{
        task_id,
        currPage
    }=payload
    return axios.get(`${REPORT_MODULE}/getList`,{
        task_id,
        currPage
    })
    .then(res=>{
        return res.data;
    })
}


/**
 * 浏览报告详情
 * @param {*} payload
 * @returns
 */
 export const getReport=payload=>{
    const{
        reportId
    }=payload
    return axios.get(`${REPORT_MODULE}/getReport`,{
        reportId
    })
    .then(res=>{
        return res.data;
    })
}



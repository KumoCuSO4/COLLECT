/*
 * @Descripttion:
 * @version:
 * @Author: 张志浩
 * @Date: 2022-05-25 19:38:24
 * @LastEditors: 张志浩
 * @LastEditTime: 2022-05-25 21:42:58
 */

import http from '@/network';

/**
 * User Register
 * @param {*} data 
 * @returns 
 */
export function register(data) {
  return http(
    {
      url: '/user/register',
      method: 'POST',
      data
    }
  )
    .then(value => {
      return value.data;
    })
    .catch(reason => {
      return reason;
    })
}

/**
 * User Login
 * @param {*} data 
 * @returns 
 */
export function login(data) {
  return http(
    {
      url: '/user/login',
      method: 'POST',
      data
    }
  )
    .then(value => {
      return value.data;
    })
    .catch(reason => {
      return reason;
    })
}

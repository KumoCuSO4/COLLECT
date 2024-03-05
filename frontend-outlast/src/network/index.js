import qs from 'qs';
import axios from 'axios';

const axiosIns = axios.create({
  baseURL: '/api',
  timeout: 5000,
  headers: {
    'Content-type': 'application/json'
  },
  transformRequest(data, headers) {
    const contentType = headers['Content-Type'];
    if (contentType === 'application/x-www-form-urluncoded') return qs.stringify(data);
    return data;
  }
})

axiosIns
  .interceptors
  .response
  .use(response => {
    return response.data;
  }, reason => {
    return Promise.reject(reason);
  })

axiosIns
  .interceptors
  .request
  .use(config => {
    const token = localStorage.getItem('token');
    if (token) {
      // do something...
    }
    return config;
  }, reason => {
    return Promise.reject(reason);
  })

export default axiosIns;
import axios from '../../axios'

export const findPage = (data) => {
    return axios({
        url: '/stu/Dormitory/findPage',
        method: 'post',
        data,
    })
}
export const add = (data) => {
    return axios({
        url: '/stu/Dormitory/add',
        method: 'post',
        data,
    })
}
export const del = (data) => {
    return axios({
        url: '/stu/Dormitory/del/'+data,
        method: 'post',
        // data,
    })
}
export const dels = (data) => {
    return axios({
        url: '/stu/Dormitory/dels/'+data,
        method: 'post',
        // data,
    })
}
export const findAll = () => {
    return axios({
        url: '/stu/Dormitory/findAll/',
        method: 'post',
        // data,
    })
}

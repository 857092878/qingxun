import axios from '../../axios'

export const findPage = (data) => {
    return axios({
        url: '/stu/ClassRole/findPage',
        method: 'post',
        data,
    })
}
export const add = (data) => {
    return axios({
        url: '/stu/ClassRole/add',
        method: 'post',
        data,
    })
}
export const del = (data) => {
    return axios({
        url: '/stu/ClassRole/del/'+data,
        method: 'post',
        // data,
    })
}
export const dels = (data) => {
    return axios({
        url: '/stu/ClassRole/dels/'+data,
        method: 'post',
        // data,
    })
}
export const findAll = () => {
    return axios({
        url: '/stu/ClassRole/findAll/',
        method: 'post',
        // data,
    })
}

import axios from '../../axios'

export const findPage = (data) => {
    return axios({
        url: '/stu/Politics/findPage',
        method: 'post',
        data,
    })
}
export const add = (data) => {
    return axios({
        url: '/stu/Politics/add',
        method: 'post',
        data,
    })
}
export const del = (data) => {
    return axios({
        url: '/stu/Politics/del/'+data,
        method: 'post',
        // data,
    })
}
export const dels = (data) => {
    return axios({
        url: '/stu/Politics/dels/'+data,
        method: 'post',
        // data,
    })
}
export const findAll = () => {
    return axios({
        url: '/stu/Politics/findAll/',
        method: 'post',
        // data,
    })
}

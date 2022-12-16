import axios from '../../axios'


export const findAll = () => {
    return axios({
        url: '/stu/HealthStatus/findAll/',
        method: 'post',
        // data,
    })
}

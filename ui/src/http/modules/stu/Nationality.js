import axios from '../../axios'

export const findAll = () => {
    return axios({
        url: '/stu/Nationality/findAll/',
        method: 'post',
        // data,
    })
}

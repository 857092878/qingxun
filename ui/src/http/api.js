/* 
 * 接口统一集成模块
 */
//1.以下是系统管理模块
import * as login from './modules/sys/login'
import * as user from './modules/sys/user'
import * as dept from './modules/sys/dept'
import * as role from './modules/sys/role'
import * as menu from './modules/sys/menu'
import * as dict from './modules/sys/dict'
import * as config from './modules/sys/config'
import * as log from './modules/sys/log'
import * as loginlog from './modules/sys/loginlog'
import * as func from './modules/sys/function'



//3.测试模块
import * as TstAllshow from './modules/tst/TstAllshow'
import * as TstTreeshow from './modules/tst/TstTreeshow'
import * as TstPageshow from './modules/tst/TstPageshow'
import * as StudentAllshow from './modules/tst/StudentAllshow'



//疫情防控管理系统
import * as ClassRole from './modules/stu/ClassRole'
import * as Healthgrade from './modules/stu/Healthgrade'
import * as Politics from './modules/stu/Politics'
import * as Accommodation from './modules/stu/Accommodation'
import * as Acid from './modules/stu/Acid'
import * as Antiepidemic from './modules/stu/Antiepidemic'
import * as Dormitory from './modules/stu/Dormitory'
import * as Student from './modules/stu/Student'
import * as Nationality from './modules/stu/Nationality'
import * as HealthStatus from './modules/stu/HealthStatus'




// 默认全部导出
export default {
    //1.以下是系统管理模块
    login,
    user,
    dept,
    role,
    menu,
    dict,
    config,
    log,
    loginlog,
    func,

    

    //3.测试模块
    TstAllshow,
    TstTreeshow,
    TstPageshow,
    StudentAllshow,


    //疫情防控系统
    ClassRole,
    Healthgrade,
    Politics,
    Accommodation,
    Acid,
    Antiepidemic,
    Dormitory,
    Student,
    Nationality,
    HealthStatus

}
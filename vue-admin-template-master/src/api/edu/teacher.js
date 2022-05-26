import request from '@/utils/request'

export default{
    getTeacherListPage(current,limit,teacherQuery){
        return request({
          url: `/eduService/teacher/pageTeacherCondition/${current}/${limit}`,
          method: 'post',
          data: teacherQuery  
        })
    },
    deleteTeacherId(id){
        return request({
            url: `/eduService/teacher/${id}`,
            method: 'delete'
        })
    },
    addTeacher(teacher){
        return request({
            url:`/eduService/teacher/addTeacher`,
            method: 'post',
            data: teacher
        })
    },
    getTeacherInfo(id){
        return request({
            url:`/eduService/teacher/getTeacher/${id}`,
            method:'get'
        })
    },
    updateTeacherInfo(teacher){
        return request({
            url:`/eduService/teacher/updateTeacher`,
            method:'post',
            data:teacher
        })
    }
}
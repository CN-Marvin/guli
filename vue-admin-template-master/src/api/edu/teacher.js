import request from '@/utils/request'

export default{
    getTeacherListPage(current,limit,teacherQuery){
        return request({
          url: `/edu/teacher/pageTeacherCondition/${current}/${limit}`,
          method: 'post',
          data: teacherQuery  
        })
    },
    deleteTeacherId(id){
        return request({
            url: `/edu/teacher/${id}`,
            method: 'delete'
        })
    },
    addTeacher(teacher){
        return request({
            url:`/edu/teacher/addTeacher`,
            method: 'post',
            data: teacher
        })
    },
    getTeacherInfo(id){
        return request({
            url:`/edu/teacher/getTeacher/${id}`,
            method:'get'
        })
    }
}
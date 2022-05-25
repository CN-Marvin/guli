<template>
    <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
          <el-input v-model="teacherQuery.name" placeholder="讲师名"></el-input>
      </el-form-item>
      <el-form-item>
          <el-select v-model="teacherQuery.level" placeholder="讲师头衔">
            <el-option :label="高级讲师" :value="1"></el-option>
            <el-option :label="首席讲师" :value="2"></el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="添加时间">
          <el-date-picker 
          v-model="teacherQuery.begin" 
          placeholder="选择开始时间"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
          >
          </el-date-picker>
      </el-form-item>
      <el-form-item >
          <el-date-picker 
          v-model="teacherQuery.end" 
          placeholder="选择截止时间"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
          ></el-date-picker>
      </el-form-item>
      <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
          <el-button type="default" @click="resetDate()">清空</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="list"
        border
        fit
        highlight-current-row>

        <el-table-column
            label="序号"
            width="70"
            align="center"> 
        <template slot-scope="scope">
            {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
        </el-table-column> 

        <el-table-column prop="name" label="名称" width="80" />

        <el-table-column label="头衔" width="80"> 
            <template slot-scope="scope">
                {{ scope.row.level===1?'高级讲师':'首席讲师' }}
            </template>
        </el-table-column>

        <el-table-column prop="intro" label="资历" />

        <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

        <el-table-column prop="sort" label="排序" width="60" />

        <el-table-column label="操作" width="200" align="center"> 
            <template slot-scope="scope"> 
                <router-link :to="'/teacher/edit/'+scope.row.id"> 
                    <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
                </router-link> 
                    <el-button type="danger" size="mini" icon="el-icon-delete"
                    @click="removeTeacherById(scope.row.id)">删除</el-button>
            </template>
        </el-table-column>
        
    </el-table>
    <el-pagination
        :current-page="page"
        :page-size="limit"
        :total="total"
        style="padding: 30px 0; text-align: center;"
        layout="total, prev, pager, next, jumper"
        @current-change="getList"
        />
    </div>
        
</template>

<script>
import teacher from '@/api/edu/teacher'
export default {
    data(){
        return {
            list:null,
            page:1,
            limit:5,
            total:0,
            teacherQuery:{}
        }
    },
    created(){
        this.getList()
    },
    methods:{
        //获得讲师列表
        getList(page=1){
            this.page = page
            teacher.getTeacherListPage(this.page,this.limit,this.teacherQuery)
            .then(response => {
                this.list = response.data.records
                this.total = response.data.total
            })
            .catch(error => {
                console.log(error)
            })
        },
        resetDate(){
            this.teacherQuery = {}
            this.getList()
        },
        removeTeacherById(id){
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示',{
                confirmButtonText:'',
                cancelButtonText:'',
                type:'warning'
            }).then(() => {
                teacher.deleteTeacherId(id)
                .then(response => {
                    this.$message({
                        type:'success',
                        message:'删除成功'
                    });
                    this.getList()
                })
            })
        }
    }
}
</script>
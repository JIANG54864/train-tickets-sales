<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.start" clearable size="small" placeholder="输入出发站台搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-input v-model="query.end" clearable size="small" placeholder="输入终点站台搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation />
      </div>
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="出发站台" prop="start">
            <el-input v-model="form.start" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="终点站台" prop="end">
            <el-input v-model="form.end" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="车次编号" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="开车时间" prop="starttime">
            <el-time-picker v-model="form.starttime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="到达时间" prop="endtime">
            <el-time-picker v-model="form.endtime" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="name" label="车次编号" />
        <el-table-column prop="start" label="出发站台" />
        <el-table-column prop="end" label="终点站台" />
        <el-table-column prop="starttime" label="开车时间" />
        <el-table-column prop="endtime" label="到达时间" />
        <el-table-column v-if="checkPer(['admin','train:edit','train:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudTrain from '@/api/train'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { idtrain: null, start: null, end: null, name: null, starttime: null, endtime: null }
export default {
  name: 'Train',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '车次管理', url: 'api/train', idField: 'idtrain', sort: 'idtrain,desc', crudMethod: { ...crudTrain }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'train:add'],
        edit: ['admin', 'train:edit'],
        del: ['admin', 'train:del']
      },
      rules: {
        idtrain: [
          { required: true, message: '主键id不能为空', trigger: 'blur' }
        ],
        start: [
          { required: true, message: '出发站台不能为空', trigger: 'blur' }
        ],
        end: [
          { required: true, message: '终点站台不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '车次编号不能为空', trigger: 'blur' }
        ],
        starttime: [
          { required: true, message: '开车时间不能为空', trigger: 'blur' }
        ],
        endtime: [
          { required: true, message: '到达时间不能为空', trigger: 'blur' }
        ]
      }}
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>

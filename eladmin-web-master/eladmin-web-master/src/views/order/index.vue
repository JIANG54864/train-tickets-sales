<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.passengerIdcard" clearable size="small" placeholder="请输入身份证号" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-input v-model="query.name" clearable size="small" placeholder="请输入姓名" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation />
      </div>
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="身份证号" prop="passengerIdcard">
            <el-input v-model="form.passengerIdcard" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="车次号" prop="trainId">
            <el-input v-model="form.trainId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="发车日期" prop="data">
            <el-date-picker v-model="form.data" type="date" format="yyyy-MM-dd" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="车厢号" prop="carriage">
            <el-input v-model="form.carriage" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="座位编号" prop="nameTicket">
            <el-input v-model="form.nameTicket" style="width: 370px;" />
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
        <el-table-column prop="passengerIdcard" label="身份证号" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="trainId" label="车次号" />
        <el-table-column prop="data" label="发车日期" :formatter="formatDate" />
        <el-table-column prop="carriage" label="车厢号" />
        <el-table-column prop="nameTicket" label="座位编号" />
        <el-table-column v-if="checkPer(['admin','tOrder:edit','tOrder:del'])" label="操作" width="150px" align="center">
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
import crudTOrder from '@/api/tOrder'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, passengerIdcard: null, name: null, trainId: null, data: null, carriage: null, nameTicket: null }
export default {
  name: 'TOrder',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '订单信息', url: 'api/tOrder', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTOrder }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tOrder:add'],
        edit: ['admin', 'tOrder:edit'],
        del: ['admin', 'tOrder:del']
      },
      rules: {
        id: [
          { required: true, message: '主键id不能为空', trigger: 'blur' }
        ],
        passengerIdcard: [
          { required: true, message: '身份证号不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '姓名不能为空', trigger: 'blur' }
        ],
        trainId: [
          { required: true, message: '车次号不能为空', trigger: 'blur' }
        ],
        data: [
          { required: true, message: '发车日期不能为空', trigger: 'blur' }
        ],
        carriage: [
          { required: true, message: '车厢号不能为空', trigger: 'blur' }
        ],
        nameTicket: [
          { required: true, message: '座位编号不能为空', trigger: 'blur' }
        ]
      }}
  },
  methods: {
    formatDate(row, column) {
      // 获取单元格数据
      const data = row[column.property]
      if (data == null) {
        return null
      }
      const dt = new Date(data)
      return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' '
    },
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>

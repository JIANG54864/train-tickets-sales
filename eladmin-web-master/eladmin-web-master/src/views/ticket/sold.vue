<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div>
        <!-- 搜索 -->
        <el-input v-model="query.trainId" clearable size="small" placeholder="输入车次号搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-select v-model="query.isSold" clearable size="small" placeholder="状态" class="filter-item" style="width: 90px" @change="crud.toQuery">
          <el-option v-for="item in isSoldTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
        </el-select>
        <rrOperation />
      </div>
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="buytitle" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="身份证号" prop="passengerIdcard">
            <el-input v-model="form.passengerIdcard" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="车次号" prop="trainId">
            <el-input v-model="form.trainId" style="width: 370px;" ref="mytrainId" :disabled="true" />
          </el-form-item>
          <el-form-item label="发车日期" prop="data">
            <el-date-picker v-model="form.data" type="date" format="yyyy-MM-dd" style="width: 370px;" :disabled="true" />
          </el-form-item>
          <el-form-item label="车厢号" prop="carriage">
            <el-input v-model="form.carriage" style="width: 370px;" :disabled="true" />
          </el-form-item>
          <el-form-item label="座位编号" prop="nameTicket">
            <el-input v-model="form.nameTicket" style="width: 370px;" :disabled="true" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU();buyTicket">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="trainId" label="车次号" />
        <el-table-column prop="carriage" label="车厢号" />
        <el-table-column prop="nameTicket" label="座位编号" />
        <el-table-column prop="seatType" label="座位类型" />
        <el-table-column prop="data" label="发车日期" :formatter="formatDate" />
        <el-table-column prop="isSold" label="是否售出" :formatter="formatSold" />
        <el-table-column
          fixed="right"
          label="操作"
          width="150">
          <template slot-scope="scope">
            <el-button v-if="scope.row.isSold == '1'" class="filter-item" size="mini" type="primary" @click="crud.toAdd();buyTicket(scope.row)">购票</el-button>
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
import pagination from '@crud/Pagination'

const defaultForm = { idticket: null, data: null, nameTicket: null, isSold: null, carriage: null, trainId: null, seatType: null }
export default {
  name: 'Sold',
  components: { pagination, crudOperation, rrOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '车票管理', url: 'api/ticket', idField: 'idticket', sort: 'idticket,desc', crudMethod: { ...crudTOrder },
      optShow: {
        add: false,
        edit: false,
        del: false,
        reset: true
      }})
  },
  data() {
    return {
      buytitle: '购买车票',
      isSoldTypeOptions: [
        { key: '0', display_name: '已售出' },
        { key: '1', display_name: '未售出' }
      ],
      permission: {
        add: ['admin', 'ticket:add'],
        edit: ['admin', 'ticket:edit'],
        del: ['admin', 'ticket:del']
      },
      rules: {
        idticket: [
          { required: true, message: '主键id不能为空', trigger: 'blur' }
        ],
        trainId: [
          { required: true, message: '车次号不能为空', trigger: 'blur' }
        ],
        seatType: [
          { required: true, message: '座位类型不能为空', trigger: 'blur' }
        ],
        data: [
          { required: true, message: '发车日期不能为空', trigger: 'blur' }
        ],
        nameTicket: [
          { required: true, message: '座位编号不能为空', trigger: 'blur' }
        ],
        isSold: [
          { required: true, message: '是否售出不能为空', trigger: 'blur' }
        ],
        carriage: [
          { required: true, message: '车厢号不能为空', trigger: 'blur' }
        ]
      }}
  },
  methods: {
    buySuccses(row) {
      console.log(row)
      if (row.isSold === '1') {
        row.isSold = '0'
      } else { row.isSold = '0' }
    },
    buyTicket(row) {
      this.form.trainId = row.trainId
      this.form.data = row.data
      this.form.carriage = row.carriage
      this.form.nameTicket = row.nameTicket
      if (row.isSold === '1') {
        row.isSold = '0'
      } else { row.isSold = '0' }
    },
    formatSold(row, column) {
      // 获取单元格数据
      const data = row[column.property]
      if (data == null) {
        return null
      }
      if (data === '1') {
        return '否'
      } else {
        return '是'
      }
    },
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

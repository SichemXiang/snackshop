<template>
  <div class="chart-container">
    <div id="myChart123" :style="{width: '1000px', height: '550px'}"></div>
    <div class="button-wrapper">
      <button class="time-range-btn" @click="getDailyTransactionStats(0)">今天</button>
      <button class="time-range-btn" @click="getDailyTransactionStats(1)">昨天</button>
      <button class="time-range-btn" @click="getDailyTransactionStats(7)">过去7天</button>
      <button class="time-range-btn" @click="getDailyTransactionStats(30)">过去30天</button>
      <button class="time-range-btn" @click="getDailyTransactionStats(60)">过去60天</button>
      <button class="time-range-btn" @click="getDailyTransactionStats(365)">过去365天</button>
    </div>
  </div>


</template>

<script setup>
import { ref, onMounted ,watch} from "vue";
import * as echarts from 'echarts';
import axios from "@/util/axios";

const findSalesList = ref([]);
const x = ref([]);
const totalOrders = ref([]);
const dailyTotalPrice = ref([]);
let myChart;  // 定义图表实例

const getDailyTransactionStats = async (days) => {
  const res = await axios.get("/goodsSales/getDailyTransactionStats/" + days);
  console.log(res)
  findSalesList.value = res.data.data;
  x.value = findSalesList.value.map(item => item.transactionDate);
  totalOrders.value = findSalesList.value.map(item => item.totalOrders);
  dailyTotalPrice.value = findSalesList.value.map(item => item.dailyTotalPrice)

  updateChart();
}
const updateChart = () => {
  if (myChart) {
    myChart.dispose(); // 销毁旧的图表实例
  }

  myChart = echarts.init(document.getElementById("myChart123")); // 创建新的图表实例

  myChart.setOption({
    title: {
      show: true,
      text: '销量和金额统计折线图',
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['每日订单数', '每日总金额']
    },
    xAxis: {
      type: 'category',
      data: x.value,
      axisLabel: {
        rotate: -45
      }
    },
    yAxis: [
      {
        type: 'value',
        name: '每日订单数',

      },
      {
        type: 'value',
        name: '每日总金额'
      }
    ],
    series: [
      {
        name: '每日订单数',
        type: 'line',
        data: totalOrders.value
      },
      {
        name: '每日总金额',
        type: 'line',
        yAxisIndex: 1,
        data: dailyTotalPrice.value
      }

    ]
  });

  window.onresize = function () {
    myChart.resize();
  };
}



const calculateSummary = () => {

}

onMounted(() => {
  getDailyTransactionStats(7);
});

watch(findSalesList, calculateSummary);
</script>

<style lang="scss" scoped>

.chart-container {
  position: relative;
}

.button-wrapper {
  position: absolute;
  top: 100px;
  left: 1000px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}



.time-range-btn {
  padding: 8px 16px;  /* 设置按钮内边距 */
  font-size: 14px;   /* 设置按钮文字大小 */
  background-color: #007bff;  /* 设置按钮背景色 */
  color: #fff;       /* 设置按钮文字颜色 */
  border: none;      /* 移除按钮边框 */
  border-radius: 4px;  /* 设置按钮圆角 */
  cursor: pointer;   /* 设置鼠标指针样式为手型 */
  margin-right: 10px;  /* 设置按钮之间的右侧间距 */
}

.time-range-btn:hover {
  background-color: #0056b3;  /* 设置鼠标悬停时的按钮背景色 */
}



</style>

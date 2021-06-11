<template>
    <div id="app">
        <router-link to="/">홈페이지</router-link>
        <router-link to="/list">리스트페이지</router-link>
        <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  data(){
    return {
    }
  },
  components: {
  }
}
</script>

<!--
<router-link :to="{ name: 'Login' }">Login</router-link>
<template>
  <div>
    Search
    <select name="search" onchange="handleOnChange(this)">
      <option value="place">장소</option>
      <option value="youtuber">유투버</option>
    </select>
    <button @click="search">검색</button>
  </div>
  <div id="app">
    <ul>
        <li v-for="item in items">
        {{item.id}
        </li>
    </ul>
  </div>
</template>

<script>
import {mapGetters, mapActions } from 'vuex'
import axios from 'axios' // 아까 받은 axios 패키지를 사용하기 위해 import한다
export default {
   items : [
    { id:1,
      name: "입짧은 햇님"
    },
    { id:2,
      name: "여수 언니"
    }
    ],
   data: () => {
              return {
                  selectItem: ''
              };
          },
  handleOnChange(e) {
    // 선택된 데이터의 텍스트값 가져오기
    const text = e.options[e.selectedIndex].text;

    // 선택한 텍스트 출력
    document.getElementById('result').innerText
      = text;
  },
  search () {
              axios.get('http://127.0.0.1:9000/api/v1/place')
                  .then(result => {
                      console.log(res.data)

                  })
                  .catch(e => {
                      console.log('error : ', e)          // 에러가 나는 경우 콘솔에 에러를 출력한다
                  })
   }
}

</script>
-->
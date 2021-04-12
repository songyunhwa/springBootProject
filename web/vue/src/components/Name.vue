<template>
    <div class="name">
        오늘 해야 할 일
        <div>
                {{names}}
        </div>
    </div>
</template>

<script>
    import axios from 'axios' // 아까 받은 axios 패키지를 사용하기 위해 import한다

    export default {
        name: 'name',
        data: () => {
            return {
                names : '',
            }
        },
        created () {           // 초기화 함수를 정의 한다.
            axios.get('http://127.0.0.1:9000/api/main')
                .then(result => {
                    this.names = result.data;

                })
                .catch(e => {
                    console.log('error : ', e)          // 에러가 나는 경우 콘솔에 에러를 출력한다
                })
        }
    }
</script>
<style>
    h1, h2 {
        font-weight: normal;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #35495E;
    }
</style>
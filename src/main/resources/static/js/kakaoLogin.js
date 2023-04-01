Kakao.init('0c0bdf26e71cb335202f8262cdcb5475'); //발급받은 키 중 javascript키를 사용해준다.
//console.log(Kakao.isInitialized()); // sdk초기화여부판단
kakaoLogout();

//카카오로그인
function loginWithKakao(){
    Kakao.Auth.login({
        success: function(response){
            Kakao.API.request({
                url:'/v2/user/me',
                success: function(response){
                let kakaoForm = document.createElement("form");
                    kakaoForm.setAttribute("charset", "UTF-8");
                    kakaoForm.setAttribute("method", "post");
                    kakaoForm.setAttribute("action", "apiLogin");
                    document.body.appendChild(kakaoForm);

                console.log(response);

                const userId = "kakao" + response.id;
                console.log(userId);

                const input1 = document.createElement('input');
                input1.type = "hidden"; input1.name = "id"; input1.value = userId;
                kakaoForm.append(input1);

                kakaoForm.submit();

            },
            fail: function(error){
                console.log(error)
            },
        })
    },
    fail: function(error){
        console.log(error)
        },
    })
}
//카카오로그아웃
function kakaoLogout(){
    if(Kakao.Auth.getAccessToken()){
        Kakao.API.request({
            url: '/v1/user/unlink',
            success: function(response){
            },
            fail: function(error){
                console.log(error)
            },
        })
        Kakao.Auth.setAccessToken(undefined)
    }
}

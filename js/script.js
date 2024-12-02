// script.js
const BASE_URL = 'http://8.134.189.141:8080/account';

// 切换注册表单显示与隐藏
function toggleRegisterForm() {
    const registerForm = document.getElementById('register-form');
    const showRegisterButton = document.getElementById('show-register-button');

    if (registerForm.classList.contains('hidden')) {
        registerForm.classList.remove('hidden');
        showRegisterButton.textContent = 'Hide Register';
    } else {
        registerForm.classList.add('hidden');
        showRegisterButton.textContent = 'Register';
    }
}

// 登录
function login() {
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;

    const loginData = {
        username: username,
        password: password
    };

    fetch(`${BASE_URL}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('登录成功');
            // 保存 token
            const token = data.data.token;
            const role = data.data.role;
            localStorage.setItem('token', token);
            localStorage.setItem('role', role);
            if (role === 'Doctor') {
                window.location.href = 'doctorProfile.html';
            } else if (role === 'Patient') {
                window.location.href = 'patientProfile.html';
            } else if (role === 'Administrator') {
                window.location.href = 'adminPatientList.html';
            }
        } else {
            alert('登录失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 注册
function register() {
    const username = document.getElementById('register-username').value;
    const password = document.getElementById('register-password').value;
    const role = document.getElementById('register-role').value;
    const name = document.getElementById('register-name').value;

    const registerData = {
        username: username,
        password: password,
        role: role,
        name: name
    };

    fetch(`${BASE_URL}/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(registerData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('注册成功');
        } else {
            alert('注册失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// Shared script for login, register, and todos pages
const SERVER_URL = "http://localhost:8080";
const token = localStorage.getItem("token");

// Login page logic
function login() {
    const email=document.getElementById("email").value;
    const password=document.getElementById("password").value;

    fetch(`${SERVER_URL}/auth/login`,{
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify({email,password})
    })
    .then(response => {
        if(response.ok){
            alert("Registeration successful");
            window.location.href="todos.html"
        }
        return response.json();
    })
    .then(data => {
        localStorage.setItem("token",data.token);
        window.location.href="todos.html";
    })
    .catch(error=>{
        alert("Error Occured");
    })

}

// Register page logic
function register() {
    const email=document.getElementById("email").value;
    const password=document.getElementById("password").value;

    fetch(`${SERVER_URL}/auth/Register`,{
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify({email,password})
    })
    .then(response => {
        if(response.ok){
            alert("Registeration successful");
            window.location.href="login.html"
        }else{
            return response.json().then(data => {throw new Error(data.message || "User already Exist")});
        }
    })
    .catch(error => {
        alert("Error occured");
    })
}

// Todos page logic
function createTodoCard(todo) {
    const card=document.createElement("div");
    card.className="todo-card";

    const checkbox=document.createElement("input");
    checkbox.type="checkbox";
    checkbox.checked=todo.isCompleted;
    checkbox.addEventListener("change",function(){
        const updatedtodo={...todo,isCompleted:checkbox.checked}
        updatedTodoStatus(updatedtodo);
    });

    const span=document.createElement("span");
    span.textContent=todo.title;

    if(todo.isCompleted){
        span.style.textDecoration="line-through";
        span.style.color="#aaa";
    }

    const deletebtn=document.createElement("button");
    deletebtn.textContent="X";
    deletebtn.onclick=function() {
        deleteTodo(todo.id);
    };

    card.appendChild(checkbox);
    card.appendChild(span);
    card.appendChild(deletebtn);

    return card;
}

function loadTodos() {
    if(!token){
        alert("Please login first!");
        window.location.href="login.html";
    }

    fetch(`${SERVER_URL}/todo/all`,{
        method:"GET",
        headers:{
            Authorization:`Bearer ${token}`
        }
    })
    .then(response => {
        if(!response.ok){
            throw new Error(data.message || "Failed to Load")
        }
        return response.json();
    })
    .then((todos) => {
        const todoList = document.getElementById("todo-list");
        todoList.innerHTML="";

        if(!todos || todos.length===0){
            todoList.innerHTML=`<p id="empty-message>No todos yet</p>"`;
        }
        else{
            todos.forEach(todo => {
                todoList.appendChild(createTodoCard(todo));
            });
        }
    })
    .catch(error => {
        document.getElementById("todo-list").innerHTML=`<p style="color:red">Failed to load todos!</p>`;
    })

}

function addTodo() {
    const input=document.getElementById("new-todo");
    const todotext=input.value.trim();

    if(!todotext) return;

    fetch(`${SERVER_URL}/todo/create`,{
        method:"POST",
        headers:{
            Authorization:`Bearer ${token}`,
            "Content-Type":"application/json"
        },
        body:JSON.stringify({title: todotext, isCompleted: false})
    })
    .then(response => {
        if(!response.ok){
            throw new Error(data.message || "Failed to create Todo");
        }
        return response.text();
    })
    .then((newTodo)=> {
        input.value="";
        loadTodos();
    })
    .catch(error => {
        alert(error.message);
    })

}

function updateTodoStatus(todo) {
    fetch(`${SERVER_URL}/todo/update`,{
        method:"PUT",
        headers:{
            Authorization: `Bearer ${token}`,
            "Content-Type":"application/json"
        },
        body:JSON.stringify(todo)
    })
    .then(response => {
        if(!response.ok){
            throw new Error(data.message || "Failed to Update todo");
        }
        return response.text();
    })
    .then(()=>loadTodos())
    .error(error => {
        alert(error.message);
    })
}

function deleteTodo(id) {
    fetch(`${SERVER_URL}/todo/delete/${id}`,{
        method:"DELETE",
        headers:{Authorization:`Bearer ${token}`},
    })
    .then(response => {
        if(!response.ok){
            throw new Error(data.message || "Failed to delete todo");
        }
        return response.text();
    })
    .then(()=>loadTodos())
    .catch(error => {
        alert(error.message);
    })
}

// Page-specific initializations
document.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("todo-list")) {
        loadTodos();
    }
});

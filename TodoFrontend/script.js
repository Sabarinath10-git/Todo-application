// Shared script for login, register, and todos pages
//railway url
const SERVER_URL = "https://todo-application-production-3e06.up.railway.app";
const token = localStorage.getItem("token");

// Login page logic
function login() {
const email=document.getElementById("email").value;
const password=document.getElementById("password").value;
fetch(`${ SERVER_URL}/auth/login`,{method:"POST",
    headers:{'content-type':'application/json'},
    body:JSON.stringify({email,password})
})
.then(response=>
{
    if(!response.ok){
        throw new Error("login failed")
    }
    return response.json();
})
.then(data=>{
    localStorage.setItem("token",data.token);
    window.location.href="todos.html";
})
.catch(error=>{
    alert(error.message);
})
}

// Register page logic
function register() {
const email=document.getElementById("email").value;
const password=document.getElementById("password").value;
fetch(`${ SERVER_URL}/auth/register`,{method:"POST",
    headers:{'content-type':'application/json'},
    body:JSON.stringify({email,password})
})
.then(response=>
{if(response.ok){
    alert("Registration successfull");
    window.location.href="login.html";
}
else{
    return response.json().then(data=>{throw new Error("Registration failed");})
}
})

.catch(error=>{
    alert(error.message);
})
}

// Todos page logic
function createTodoCard(todo) {
    const card=document.createElement("div");
    card.className="todo-card";
    //for checkbox
   const checkbox = document.createElement("input");

checkbox.type = "checkbox";
checkbox.checked = todo.isCompleted;

checkbox.addEventListener("change", function () {

    const updtodo = {
        ...todo,
        isCompleted: checkbox.checked
    };

    console.log("Sending:", updtodo);

    updateTodoStatus(updtodo);
});

checkbox.type = "checkbox";
checkbox.checked = todo.isCompleted;

checkbox.addEventListener("change", function () {

    const updtodo = {
        ...todo,
        isCompleted: checkbox.checked
    };

    console.log("Sending:", updtodo);

    updateTodoStatus(updtodo);
});
    //for textbox
    const textarea=document.createElement("span");
    textarea.textContent=todo.title;
    if(todo.isCompleted){
        textarea.style.textDecoration="line-through";
        textarea.style.color="#d02929";
    }
    //for deleting textcont
    const deletebtn=document.createElement("button");
    deletebtn.textContent="X";
    deletebtn.onclick=function(){
        deleteTodo(todo.id);
    }
    card.appendChild(checkbox);
    card.appendChild(textarea);
    card.appendChild(deletebtn);
     return card;
}
   function loadTodos() {

    if (!token) {
        alert("Please login first");
        window.location.href = "login.html";
        return;
    }

    fetch(`${SERVER_URL}/todo/getall`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    })
    .then(response => {

        if (!response.ok) {
            throw new Error("LOAD failed: " + response.status);
        }

        return response.json();
    })
    .then(todos => {

        const todolist = document.getElementById("todo-list");

        todolist.innerHTML = "";

        if (todos.length === 0) {
            todolist.innerHTML = "<p>No todo created</p>";
        } else {
            todos.forEach(todo => {
                todolist.appendChild(createTodoCard(todo));
            });
        }
    })
    
    .catch(error => {
        alert(error.message);
        console.error(error);
    });
}

function addTodo() {

    const input = document.getElementById("new-todo");
    const todotext = input.value.trim();

    fetch(`${SERVER_URL}/todo/create`, {
        method: "POST",

        headers: {
            "content-type": "application/json",
            "Authorization": `Bearer ${token}`
        },

        body: JSON.stringify({
            title: todotext,
            isCompleted: false
        })
    })

    .then(Response => {

        if (!Response.ok) {
          throw new Error("LOAD failed: " + Response.status);
        }

        return Response.json();
    })

    .then(() => {

        input.value = "";
        loadTodos();

    })

    .catch(error => {

        alert(error.message);

    });
}

function updateTodoStatus(todo) {

    fetch(`${SERVER_URL}/todo/update/${todo.id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(todo)
    })
    .then(response => {

        console.log("PUT status:", response.status);

        if (!response.ok) {
            throw new Error("Update failed: " + response.status);
        }

        return response.json();
    })
    .then(updatedTodo => {

        console.log("Updated from backend:", updatedTodo);

        loadTodos();
    })
    .catch(error => {
        console.error(error);
        alert(error.message);
    });
}

function deleteTodo(id) {

    fetch(`${SERVER_URL}/todo/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
    .then(response => {

        if (!response.ok) {
            throw new Error("Deletion failed: " + response.status);
        }
        return;
    })
    .then(() => {
        loadTodos();
    })
    .catch(error => {
        console.error(error);
        alert(error.message);
    });
}

// Page-specific initializations
document.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("todo-list")) {
        loadTodos();
    }
});

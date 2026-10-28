"use strict";

function setError(id, message) {
    const element = document.getElementById(id);
    if (element) {
        element.textContent = message;
    }
}

function validateEmployeeForm() {
    let valid = true;

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const department = document.getElementById("department").value.trim();
    const salary = document.getElementById("salary").value.trim();

    // Clear previous errors.
    ["nameError", "emailError", "departmentError", "salaryError"]
        .forEach(id => setError(id, ""));

    if (name.length < 2 || name.length > 100 ||
        !/^[\p{L}][\p{L} .'-]*$/u.test(name)) {
        setError("nameError",
            "Enter a valid name between 2 and 100 characters.");
        valid = false;
    }

    if (email.length > 150 ||
        !/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/.test(email)) {
        setError("emailError", "Enter a valid email address.");
        valid = false;
    }

    if (!/^[A-Za-z][A-Za-z &-]{1,49}$/.test(department)) {
        setError("departmentError", "Enter a valid department.");
        valid = false;
    }

    // Positive decimal, at most two decimal places.
    if (!/^\d{1,10}(\.\d{1,2})?$/.test(salary) ||
        !Number.isFinite(Number(salary)) ||
        Number(salary) <= 0) {
        setError("salaryError",
            "Enter a positive salary with up to two decimal places.");
        valid = false;
    }

    return valid;
}

function confirmDelete() {
    return window.confirm(
        "Are you sure you want to delete this employee?"
    );
}
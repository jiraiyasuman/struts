<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Employee Form</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
    <script defer src="${pageContext.request.contextPath}/js/app.js"></script>
</head>
<body>

<div class="form-container">
    <div class="form-card">
        <h1>
            <s:if test="id > 0">Update Employee</s:if>
            <s:else>Add Employee</s:else>
        </h1>

        <p class="subtitle">Enter the employee details below.</p>

        <s:actionerror/>

        <s:form action="%{id > 0 ? 'update' : 'create'}"
                method="post"
                onsubmit="return validateEmployeeForm();"
                theme="simple">

            <s:hidden name="id"/>

            <div class="form-group">
                <label for="name">Full Name</label>
                <s:textfield name="name" id="name"
                             cssClass="form-control"
                             maxlength="100"
                             placeholder="Enter full name"/>
                <s:fielderror fieldName="name"/>
                <small id="nameError" class="client-error"></small>
            </div>

            <div class="form-group">
                <label for="email">Email Address</label>
                <s:textfield name="email" id="email"
                             cssClass="form-control"
                             maxlength="150"
                             placeholder="name@example.com"/>
                <s:fielderror fieldName="email"/>
                <small id="emailError" class="client-error"></small>
            </div>

            <div class="form-group">
                <label for="department">Department</label>
                <s:textfield name="department" id="department"
                             cssClass="form-control"
                             maxlength="50"
                             placeholder="e.g. IT, HR, Finance"/>
                <s:fielderror fieldName="department"/>
                <small id="departmentError" class="client-error"></small>
            </div>

            <div class="form-group">
                <label for="salary">Monthly Salary (INR)</label>
                <s:textfield name="salary" id="salary"
                             cssClass="form-control"
                             placeholder="e.g. 55000.00"/>
                <s:fielderror fieldName="salary"/>
                <small id="salaryError" class="client-error"></small>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <s:if test="id > 0">Update Employee</s:if>
                    <s:else>Save Employee</s:else>
                </button>

                <a class="btn btn-secondary"
                   href="${pageContext.request.contextPath}/employees">
                    Cancel
                </a>
            </div>
        </s:form>
    </div>
</div>

</body>
</html>
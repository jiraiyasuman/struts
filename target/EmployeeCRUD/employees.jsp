<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Employee Management</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
    <script defer src="${pageContext.request.contextPath}/js/app.js"></script>
</head>
<body>

<div class="container">
    <header class="page-header">
        <div>
            <h1>Employee Management</h1>
            <p>Manage employee records in one place.</p>
        </div>
        <a class="btn btn-primary"
           href="${pageContext.request.contextPath}/input">
            + Add Employee
        </a>
    </header>

    <s:actionerror/>

    <div class="table-card">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Department</th>
                    <th>Salary</th>
                    <th>Actions</th>
                </tr>
            </thead>

            <tbody>
                <s:if test="employees != null && !employees.isEmpty()">
                    <s:iterator value="employees" var="emp">
                        <tr>
                            <td><s:property value="#emp.id"/></td>
                            <td><s:property value="#emp.name"
                                           escapeHtml="true"/></td>
                            <td><s:property value="#emp.email"
                                           escapeHtml="true"/></td>
                            <td><s:property value="#emp.department"
                                           escapeHtml="true"/></td>
                            <td>
                                ₹<s:property value="#emp.salary"/>
                            </td>
                            <td class="actions">
                                <a class="btn btn-edit"
                                   href="${pageContext.request.contextPath}/edit?id=<s:property value='#emp.id'/>">
                                    Edit
                                </a>

                                <form class="inline-form"
                                      action="${pageContext.request.contextPath}/delete"
                                      method="post"
                                      onsubmit="return confirmDelete();">
                                    <input type="hidden" name="id"
                                           value="<s:property value='#emp.id'/>">
                                    <button class="btn btn-danger"
                                            type="submit">
                                        Delete
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </s:iterator>
                </s:if>

                <s:else>
                    <tr>
                        <td colspan="6" class="empty">
                            No employees found. Add your first employee.
                        </td>
                    </tr>
                </s:else>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
<#include "header.ftl">
Hello ${context.name}!
Title ${context.title}!
Request path is ${context.request().path()}

<#--Users List ${context.userList}-->
<#list context.userList>
<#items as user>
<li><a href="/wiki/${user}">${user}</a></li>
</#items>
</#list>
<#include "footer.ftl">
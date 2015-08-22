<!-- *************************************************** -->
<!-- Navigation macros                                   -->
<!-- *************************************************** -->

<#macro nodelink node prefix="" suffix="">
<#if node != cmd.sitemapNode>
<a class="navlink" href="/${toolkit.module}${node.url}">${prefix}${language.translate(node.title)}${suffix}</a><#else><span class="navlink">${prefix}${language.translate(node.title)}${suffix}</span></#if></#macro>

<#macro compass>
<#if cmd.navprev?exists>
	<div id="navprev"><@nodelink node=cmd.navprev prefix=MSG.M_NAVPREV /></div>
</#if>
<#if cmd.navparent?exists>
	<div id="navup"><@nodelink node=cmd.navparent prefix=MSG.M_NAVUP /></div>
</#if>
<#if cmd.navnext?exists>
	<div id="navnext"><@nodelink node=cmd.navnext prefix=MSG.M_NAVNEXT /></div>
</#if>
</#macro>

<#macro crumbs>
<#list cmd.navcrumbs as item>
<span class="navcrumb"><@nodelink node=item /></span><span class="navcrumbseparator">&nbsp;&gt;&nbsp;</span></#list><span class="navcrumb"><@nodelink node=cmd.sitemapNode/></span>
</#macro>

<#macro level>
<#list cmd.navchildren as child>
<span class="navchild"><@nodelink node=child /></span>
</#list>
</#macro>

<#macro tree>
<ul class="navtree">
<#list cmd.navtree as item><li class="navtree_${item.level}"><@nodelink node=item /></li></#list>
</ul>
</#macro>

<#macro link url>
<a href="${url}">${url}</a></#macro>

<#macro node name>
<@nodelink node=toolkit.sitemap.findNodeByName(name).current()/></#macro>
<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/${toolkit.module}_resources/style/main.css"/>
		<title>${language.translate(cmd.title)}</title>
	</head>
	<body>
		<div id="page">
			<div id="header">	
				<div id="navcompass"><@nav.compass/></div>
				<div id="mission">${MSG.M_MISSION} - <a href="${cmd.requestURI}?lang=en">[en]</a><a href="${cmd.requestURI}?lang=it">[it]</a></div>
				<h1>${language.translate(cmd.title)}</h1>
				<div id="navtracer">
					<div id="navcrumbs"><@nav.crumbs/></div>
					<div id="navlevel"><@nav.level/></div>
				</div>
			</div>
			<div id="body">
				<div id="navtree"><@nav.tree/></div>
				<#include "${locale.language}/${cmd.body}">
			</div>
		</div>
	</body>
</html>
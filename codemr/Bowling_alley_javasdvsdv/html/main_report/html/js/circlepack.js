codemr.circlepack=function(){var d=codemr.metricchart("class-metric-chart",classMetricSpec,classMetricValues),n=codemr.metricchart("package-metric-chart",packageMetricSpec,packageMetricValues);codemr.circlepack.paintCallBack=function(){f.style("fill",function(e){return e.children?"#C6DCEC":t(e)}),f.selectAll("title").text(function(e){return e.name+" "+EQ_getSelectedMetricName(e)+" "+EQ_GET_METRICVALUE(e)})};var i=1,u=800;function t(e){return EQ_GET_COLOR(e)}function c(e){if(0==i)return e.name;var t=e.name,n=t.indexOf(".");if(1==i)return t.substring(n+1);var c=t.indexOf(".",n+1);if(2==i)return t.substring(c+1);var r=t.indexOf(".",c+1);return t.substring(r+1)}var e=d3.layout.pack().padding(2).size([u-20,u-20]).value(function(e){return e.value}).sort(function(e,t){var n;n=t.value-e.value;return n});var r=d3.select("#circle-chart-body").append("div").attr("class","circlepackchart").append("svg").attr("width",u).attr("height",u).append("g").attr("transform","translate("+u/2+","+u/2+")");root=EQ_GET_DATA();var l,a=root,o=e.nodes(root),s=r.selectAll("circle").data(o).enter().append("circle"),p=null,f=s.attr("class",function(e){return e.parent?e.children?"node":"node node--leaf":"node node--root"}).style("fill",function(e){return e.children?"#C6DCEC":t(e)}).style("fill-opacity",function(e){return e.children?"0.5":"0.9"}).style("stroke",function(e){return"#000"}).on("click",function(e){a!==e&&(v(e),d3.event.stopPropagation())}).on("mousemove",function(e){p!==e&&(e.metrics?d.updateMetricValues(EQ_convertMetricValues(e.metricvalues)):e.pmetrics&&n.updateMetricValues(EQ_convertMetricValues(e.pmetricvalues)),p=e,"function"==typeof updateSelectedElement&&e.parent&&(e.key?updateSelectedElement(e.parent.name,e.key):updateSelectedElement(e.name,""))),g(e),function(e){movingOnElement=!0,elem=document.getElementById("circle-chart-body"),boundingRect=elem.getBoundingClientRect();var t=u,n=u,c=d.getWidth();g(e);var r=window.innerWidth||document.documentElement.clientWidth||document.body.clientWidth,i=window.innerHeight||document.documentElement.clientHeight||document.body.clientHeight,l=d3.event.pageX,a=d3.event.pageY;if(d3.event.pageX>boundingRect.left+t/2){var o=r-l;l<c&&(o=r-c),d3.select("#circle-pack-tooltip").style("right",o+"px"),d3.select("#circle-pack-tooltip").style("left","auto")}else{var s=l;r<l+c&&(s=r-c),d3.select("#circle-pack-tooltip").style("left",s+"px"),d3.select("#circle-pack-tooltip").style("right","auto")}d3.event.pageY>boundingRect.top+n/2?(d3.select("#circle-pack-tooltip").style("bottom",i-a+"px"),d3.select("#circle-pack-tooltip").style("top","auto")):(d3.select("#circle-pack-tooltip").style("top",a+"px"),d3.select("#circle-pack-tooltip").style("bottom","auto"))}(e)});s.append("title").text(function(e){return e.name+" "+EQ_getSelectedMetricName(e)+" "+EQ_GET_METRICVALUE(e)});var m=r.selectAll("text").data(o).enter().append("text");m.attr("class","circlelabel").style("display",function(e){return e.parent===root?null:"none"}).text(function(e){return c(e)});m.append("title").text(function(e){return e.name+" "+EQ_getSelectedMetricName(e)+" "+EQ_GET_METRICVALUE(e)});var y=r.selectAll("circle,text");function v(e){a=e,d3.transition().duration(d3.event.altKey?7500:750).tween("zoom",function(e){var t=d3.interpolateZoom(l,[a.x,a.y,2*a.r+20]);return function(e){h(t(e))}}).selectAll("text").filter(function(e){return!!e&&(e.parent===a||"inline"===this.style.display)}).style("fill-opacity",function(e){return e.parent===a?1:0}).each("start",function(e){e.parent===a&&(this.style.display="inline")}).each("end",function(e){e.parent!==a&&(this.style.display="none")}),e.metrics&&eQ.MetricChart.updateDots(e.metrics,c(e))}function h(t){var n=u/t[2];l=t,y.attr("transform",function(e){return"translate("+(e.x-t[0])*n+","+(e.y-t[1])*n+")"}),f.attr("r",function(e){return e.r*n})}function g(e){var t;d3.event.ctrlKey||d3.event.metaKey?e.children?(t=e).children&&t.parent&&(d.setVisible(!1),n.setVisible(!0)):(d.setVisible(!0),n.setVisible(!1)):(d.setVisible(!1),n.setVisible(!1))}d3.select("body").on("click",function(){v(root)}),h([root.x,root.y,2*root.r+20]),d3.select(self.frameElement).style("height","850px")};

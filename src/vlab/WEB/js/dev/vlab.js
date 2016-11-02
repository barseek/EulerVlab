 var startNode;
 log = [];
 saved =   [];
 counter =0;
 solution_arch = [];
 reseted = 0;
 solution_node = [];
 last_action = [];
 logDump=[];
 logNodeDump=[];
 solution=[];
 logNode = [];
 savedNode = [];
 undoFlag = 0;
 inited = false;
 var cy;


 //начинается грязь

 var a;

 function cancel() {
	 if(last_action[0]=='node' || last_action[0]=='uncheck')
	 {
		 if(log.length>0 && logNode.length>1)
		 {
			 //	if((solution.length % cy.edges().size()) == log.length)
			 //	{
			 var last = log.pop();
			 var lastNode = logNode.pop();
			 //}
			 saved.push(last);
			 savedNode.push(lastNode);

			 cy.$('#'+last).style('line-color','grey');
			 cy.$('#'+last).style('width','2');
			 cy.$('#'+last).toggleClass('selected',false);
			 var table = document.getElementById('table_body_note');


			 table.removeChild(table.childNodes[table.childNodes.length - 2]); //table.childNodes.length - 3
			 solution.pop();
			 counter--;

			 console.log('reseted');
		 }
		 else
		 if(solution_arch[solution_arch.length-1])
		 {
			 var checkbox = document.getElementById('box'+counter);
			 checkbox.checked = false;
			 var number = document.getElementById('num'+counter);
			 var solution_step = document.getElementById('sstep'+counter);
			 var checkbox_div = document.getElementById('chkdiv'+counter);
			 checkbox_div.style.backgroundColor='white';
			 solution_step.style.backgroundColor='white';
			 number.style.backgroundColor='white';
			 undump();
			 last_action= [];
			 last_action[0]='uncheck';
		 }
	 }
	 if(last_action[0]=='check')
	 {
		 //восстановить все как было до чека
		 var checkbox = document.getElementById('box'+last_action[1]);
		 checkbox.checked = false;
		 var number = document.getElementById('num'+last_action[1]);
		 var solution_step = document.getElementById('sstep'+last_action[1]);
		 var checkbox_div = document.getElementById('chkdiv'+last_action[1]);
		 checkbox_div.style.backgroundColor='white';
		 solution_step.style.backgroundColor='white';
		 number.style.backgroundColor='white';
		 undump();
		 last_action= [];
		 last_action[0]='uncheck';
	 }
	 console.log("canceled");
 }


 function reset () {
	 counter=0;
	 reseted = 1;
	 cy.edges().forEach(function(ele){ele.toggleClass('selected',false);
		 ele.style('line-color','grey');
		 ele.style('width','2');
	 });
	 log = [];
	 saved = [];
	 logNode = [];
	 solution=[];
	 solution_arch = [];
	 solution_node = [];

	 var table = document.getElementById('table_body_note');
	 while (table.firstChild) {
		 table.removeChild(table.firstChild);
	 }
	 var scrollbar =  document.createElement('div');
	 scrollbar.id="add_scroll";
	 table.appendChild(scrollbar);

	 logNode.push(startNode.id());
 };

 function dump() {

	 cy.edges().forEach(function(ele){ele.toggleClass('selected',false);
		 ele.style('line-color','grey');
		 ele.style('width','2');
	 });
	 solution_arch.push(log);
	 solution_node.push(logNode);
	 logDump = log;
	 logNodeDump = logNode;
	 log = [];
	 saved = [];
	 logNode = [];
	 console.log(solution_arch);

	 logNode.push(startNode.id());
 };

 function undump() {
	 log = solution_arch[solution_arch.length-1];
	 logNode = solution_node[solution_node.length-1];
	 logDump = [];
	 logNodeDump = [];
//console.log(log);
	 solution_arch.pop();
	 solution_node.pop();
	 for(i=0;i<log.length;i++)
	 {
		 var edge = cy.$('#'+log[i]);
		 edge.style('line-color','blue');
		 edge.style('width','2');
	 }
	 console.log(solution_arch);
 };

 function showSolution(ln) {
//counter++;
//строка
	 var table_note = document.createElement('div');
	 table_note.className="table_note";

//номер шага
	 var number = document.createElement('div');
	 number.id = 'num'+counter;
	 number.className="ndiv_span";
	 number.innerHTML=counter;
	 number.style.cssText='width: 10%';

//сам шаг
	 var solution_step = document.createElement('div');
	 solution_step.className="ndiv_span";
	 solution_step.id = 'sstep'+counter;
	 solution_step.style.cssText='width: 75%; text-align: center;';
	 solution_step.innerHTML=ln;


//чекбокс
	 var checkbox_div = document.createElement('div');
	 checkbox_div.id = 'chkdiv'+ counter;
	 checkbox_div.className="ndiv_span";
	 checkbox_div.style.cssText='width: 15%';
	 var checkbox = document.createElement('input');
	 checkbox.type = "checkbox";
	 checkbox.id = "box"+counter;
	 checkbox.className = "check";
	 checkbox.onclick = function(){

		 if (this.id!="box"+counter) {

			 if(this.checked) this.checked=true;
			 
			 checkbox.checked=false;


		 }
		 else{

			 if(this.checked){
				 last_action=[];
				 checkbox_div.style.backgroundColor='yellow';
				 solution_step.style.backgroundColor='yellow';
				 number.style.backgroundColor='yellow';
				 last_action.push('check');
				 last_action.push(counter);
				 //solution_arch.push(solution[solution.length-1]);
				 //console.log(solution_arch);
				 dump();
				 //solution_step.setAttribute('bgcolor','yellow');
				 //checkbox_div.setAttribute('bgcolor','yellow');


			 }
			 else{
				 checkbox_div.style.backgroundColor='white';
				 solution_step.style.backgroundColor='white';
				 number.style.backgroundColor='white';
				 undump();
				 last_action=[];
				 last_action.push('uncheck');
				 last_action.push(counter);
			 }
		 }
	 };
	 checkbox_div.appendChild(checkbox);
	 var scrollbar = document.getElementById('add_scroll');
	 table_note.appendChild(number);
	 table_note.appendChild(solution_step);
	 table_note.appendChild(checkbox_div);

	 var table_body_note = document.getElementById('table_body_note');
	 table_body_note.insertBefore(table_note,scrollbar);

	 solution.push(ln.toString());


 };


var inited = false;



 /*

  elements: [ // list of graph elements to start with
  { // node a
  data: { id: 'a' }
  },
  { // node b
  data: { id: 'b' }
  },
  { // node c
  data: { id: 'c' }
  },
  { // node d
  data: { id: 'd' }
  },
  { // node e
  data: { id: 'e' }
  },
  { // node f
  data: { id: 'f' }
  },
  { // edge ab
  data: { id: 'ab', source: 'a', target: 'b' }
  },
  { // edge ac
  data: { id: 'ac', source: 'a', target: 'c' }
  },
  { // edge ae
  data: { id: 'ae', source: 'a', target: 'e' }
  },
  { // edge af
  data: { id: 'af', source: 'a', target: 'f' }
  },
  { // edge bc
  data: { id: 'bc', source: 'b', target: 'c' }
  },
  { // edge bd
  data: { id: 'bd', source: 'b', target: 'd' }
  },
  { // edge be
  data: { id: 'be', source: 'b', target: 'e' }
  },
  { // edge cd
  data: { id: 'cd', source: 'c', target: 'd' }
  },
  { // edge ce
  data: { id: 'ce', source: 'c', target: 'e' }
  },
  { // edge de
  data: { id: 'de', source: 'd', target: 'e' }
  },
  { // edge df
  data: { id: 'df', source: 'd', target: 'f' }
  }
  ],
  */

 var mas = [];
 var num =0;
 mas[num] = [];




 var Vlab = {

	div : null,
	edges : "",
	setVariant : function(str){},
	setPreviosSolution: function(str){

		var previousSolution = document.getElementById("previousSolution");
		previousSolution.value("sometextforjava");
		alert(Vlab.edges);
	},
	setMode: function(str){},
	helpId : true,
	showHelp : function () {

		var graph  = document.getElementById('graph');
		var info  = document.getElementById('info');
		var help  = document.getElementById('help');

		if (this.helpId)
		{
			graph.style.display='none';
			info.style.display='none';
			help.style.display='block';
			this.helpId=false;
		}
		else
		{
			help.style.display='none';
			graph.style.display='block';
			info.style.display='block';
			this.helpId=true;
		}

		
	},
	//Инициализация ВЛ



	init : function() {
		if (inited == true) {
			document.getElementById("jsLab").innerHTML="";
			logNode = [];
		}
		inited=true;
		this.div = document.getElementById("jsLab");
		this.div.innerHTML = this.window;
		//document.getElementById("tool").innerHTML = this.tool;

		var preGeneratedCode = document.getElementById("preGeneratedCode");
		if (preGeneratedCode != undefined) {

			if (preGeneratedCode.value.indexOf("~~~") > -1) {
				this.edges = preGeneratedCode.value.split("~~~")[0];


			} else {
				this.edges = preGeneratedCode.value;
			}


		}


		cy = cytoscape({
			// very commonly used options:
			container: document.getElementById('cy'),


			elements: [ // list of graph elements to start with
				{ // node a
					data: { id: 'a' }
				},
				{ // node b
					data: { id: 'b' }
				},
				{ // node c
					data: { id: 'c' }
				},
				{ // node d
					data: { id: 'd' }
				},
				{ // node e
					data: { id: 'e' }
				},
				{ // node f
					data: { id: 'f' }
				},
				/*
				 { // edge ab
				 data: { id: 'ab', source: 'a', target: 'b' }
				 },
				 { // edge ac
				 data: { id: 'ac', source: 'a', target: 'c' }
				 },
				 { // edge ae
				 data: { id: 'ae', source: 'a', target: 'e' }
				 },
				 { // edge af
				 data: { id: 'af', source: 'a', target: 'f' }
				 },
				 { // edge bc
				 data: { id: 'bc', source: 'b', target: 'c' }
				 },
				 { // edge bd
				 data: { id: 'bd', source: 'b', target: 'd' }
				 },
				 { // edge be
				 data: { id: 'be', source: 'b', target: 'e' }
				 },
				 { // edge cd
				 data: { id: 'cd', source: 'c', target: 'd' }
				 },
				 { // edge ce
				 data: { id: 'ce', source: 'c', target: 'e' }
				 },
				 { // edge de
				 data: { id: 'de', source: 'd', target: 'e' }
				 },
				 { // edge df
				 data: { id: 'df', source: 'd', target: 'f' }
				 }
				 */
			],

			style: [ // the stylesheet for the graph
				{
					selector: 'node',
					style: {
						'color': 'black',
						'background-color': '#9c27b0',
						'label': 'data(id)',
						'text-halign' : 'center',
						'text-valign' : 'center',
						'height':'30px',
						'width':'30px',
					}
				},

				{
					selector: 'edge',
					style: {

						'curve-style':'unbundled-bezier',
						'width': 2
						,
						'line-color': 'grey',
						'target-arrow-shape': 'none'
					}
				},
				{
					selector: '.edge_point',
					style: {
						'line-color': '#C40E14'
					}
				},
				{
					selector: '.edge_hide',
					style: {
						'opacity': '0.1'
					}
				},
				{
					selector: '.edge_common',
					style: {
						'line-color': '#070665'
					}
				}
			],

			layout: {
				name: 'breadthfirst',
				directed: true,
				roots: '#a',
				padding: 10
			},




			// initial viewport state:
			zoom: 1,
			pan: { x: 0, y: 0 },

			// interaction options:
			minZoom: 1e-50,
			maxZoom: 1e50,
			zoomingEnabled: true,
			userZoomingEnabled: true,
			panningEnabled: false,
			userPanningEnabled: true,
			boxSelectionEnabled: false,
			selectionType: 'single',
			touchTapThreshold: 8,
			desktopTapThreshold: 4,
			autolock: false,
			autoungrabify: true,
			autounselectify: false,

			// rendering options:
			headless: false,
			styleEnabled: true,
			hideEdgesOnViewport: false,
			hideLabelsOnViewport: false,
			textureOnViewport: false,
			motionBlur: false,
			motionBlurOpacity: 0.2,
			wheelSensitivity: 1,
			pixelRatio: 'auto',
			renderer: { /* ... */ }




		});
		/*	for(var i = 0; i < mas.length; i++)



		 }
		 */
		var options = {
			name: 'circle',

			fit: true, // whether to fit the viewport to the graph
			padding: 30, // the padding on fit
			boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
			avoidOverlap: true, // prevents node overlap, may overflow boundingBox and radius if not enough space
			radius: undefined, // the radius of the circle
			startAngle: 3/2 * Math.PI, // where nodes start in radians
			sweep: undefined, // how many radians should be between the first and last node (defaults to full circle)
			clockwise: true, // whether the layout should go clockwise (true) or counterclockwise/anticlockwise (false)
			sort: undefined, // a sorting function to order the nodes; e.g. function(a, b){ return a.data('weight') - b.data('weight') }
			animate: false, // whether to transition the node positions
			animationDuration: 500, // duration of animation in ms if enabled
			animationEasing: undefined, // easing of animation if enabled
			ready: undefined, // callback on layoutready
			stop: undefined // callback on layoutstop

		};
		cy.layout( options );


		cy.on('click','node', function(evt){

			last_action=[];
			last_action.push('node');
			last_action.push(evt.cyTarget.id());
			if(logNode.length==0 || last_action[0]=='uncheck'){
				//counter=0;
				logNode.push(evt.cyTarget.id());
				//evt.cyTarget.style('background-color','black');
			}

			else
			{
				var edge = cy.$('#'+logNode[logNode.length-1]).edgesWith(evt.cyTarget);

				var bool = true;
				edge.forEach(function(edg){
					for(i=0;i<log.length;i++)
					{
						if(edg.id()==log[i]) { bool=false; break;}
					}
				});

				if (edge.length>0 && bool){
					logNode.push(evt.cyTarget.id());
					//evt.cyTarget.style('background-color','black');
					cy.$('#'+edge.id()).style('line-color','blue');
					cy.$('#'+edge.id()).style('width','2');
					edge.forEach(function(edg){log.push(edg.id())});
					undoFlag=0;
					counter++;
					showSolution(logNode);
				}
			}



		});
		cy.on('mouseover', 'edge', function (evt) {
			var edge = evt.cyTarget;
			var id = "#" + edge.id();
			cy.edges().forEach(function (ele, i, eles) {
				ele.addClass("edge_hide");
			});
			cy.$(id).removeClass("edge_hide");
			cy.$(id).addClass("edge_point");
		});

		cy.on('mouseout', 'edge', function (evt) {
			var edge = evt.cyTarget;
			var id = "#" + edge.id();
			cy.edges().forEach(function (ele, i, eles) {
				ele.removeClass("edge_hide");
			});
			cy.$(id).removeClass("edge_point");
		});

		cy.on('mouseover', 'node', function (evt) {
			var edge = evt.cyTarget;
			var id = "#" + edge.id();
			cy.$(id).css('background-color', '#F5E794');
			cy.edges().forEach(function (ele, i, eles) {
				if (ele.source().id() == edge.id() || ele.target().id() == edge.id()) {
					ele.addClass("edge_common");
				}
				else {
					ele.addClass("edge_hide");
				}

			});
		});

		cy.on('mouseout', 'node', function (evt) {
			var edge = evt.cyTarget;
			var id = "#" + edge.id();
			cy.$(id).removeCss();
			cy.edges().forEach(function (ele, i, eles) {
				if (ele.source().id() == edge.id() || ele.target().id() == edge.id()) {
					ele.removeClass('edge_common');
				}
				else {
					ele.removeClass("edge_hide");
				}
			});
			//var a = cy.$('#a');
			startNode.css('background-color', 'red');
		});
		counter=0;

		//mas = this.edges.split(';');

		console.log(this.edges);
		var jsonData = JSON.parse(this.edges);

		startNode = cy.$('#'+jsonData.startNode);
		console.log(jsonData.startNode+" is start node");
		startNode.css('background-color', 'red');
		logNode.push(startNode.id());

		for (var i = 0; i < jsonData.variant.length; i++) {
			var jsonEdge = jsonData.variant[i];
			cy.add(jsonEdge);
		}
		//начинается блок с графом
		console.log(this.edges);



		//старый парсер
		/*
		for (var i = 0; i < mas.length; i++)
		{
			if(i==0)
			{
				startNode = cy.$('#'+mas[i]);
				console.log(mas[i]+" is start node");
				startNode.css('background-color', 'red');
				logNode.push(startNode.id());
			}
			else
			{
			var chars = new Array(mas[i].length);
			chars[0] = mas[i].charAt(0);
			chars[1] = mas[i].charAt(1);
			console.log(chars);
			cy.add({ group: "edges", data: { id: mas[i], source: chars[0], target: chars[1] } });
			}
		}
		*/
		//получение варианта задания




		//Vlab.calculate();
	},
	generate: function (dataRaw) {

	},
	calculate : function(){

	},
	getCondition: function(){},
	getResults: function() {
		//здесь поменял  solution_arch na solution_node
		var count = document.getElementById("textbox").value;
		if (count==null || count=="") count =0;
		//var res = count+";";
		
		var solution = {
			solutionCounter : "",
			solutions : []
		};

		solution.solutionCounter=count;


		if (solution_node.length > 0)
		{
			for (var i = 0; i < solution_node.length; i++)
			{
				var cycle = new Array(solution_node[i]);
				var oneSolution = {
					solutionID : "",
					content : []
				}
				oneSolution.solutionID="solutionID"+i;
				//for(var j = 0; j < cycle.length; j++)
				//{

					//oneSolution.content.push(solution_node[i])

				//}
				oneSolution.content = solution_node[i];
				solution.solutions.push(oneSolution);

			}
		}
		reset();

		return JSON.stringify(solution);
	},
	calculateHandler: function(text, code){},
	window: '<div class="general"><div class="holder" ><div id="header"><div id="header-name">Поиск всех циклов Эйлера в неографе</div><div id="div_info"><input type="button" class="btn btn-info" value="Справка" id="but_info" onclick="Vlab.showHelp()"/></div></div><div id="graph"><div id="cy"></div></div><div id="info" class="info"><div id="title_matr">Таблица с ходом решения</div><div id="table-solut"><div id="table_head"><div class="div_span" style="width: 10%; border-right: 1px solid #ddd; height: 100%">№</div><div class="div_span" style="width: 72%; border-right: 1px solid #ddd; height: 100%">Цепь</div><div class="div_span" style="width: 18%; height: 100%">Цикл Эйлера?</div></div><div id="table_body_note"><div id="add_scroll"></div></div><div class="al_cent"> <text_answer>Количество циклов<input required type="text" id="textbox"></text_answer></label></div><div id="table_but"><div class="al_left"><button id="safe" type="button" onclick="cancel();" class="btn btn-link">Отменить</button></div><div class="al_right"><button id="dang" type="button" onclick="reset()" class="btn btn-link">Очистить</button></div></div></div></div><div id="help" style="display:none"><div id="div_help"> <div id="title_help">ВИРТУАЛЬНАЯ ЛАБОРАТОРИЯ ДЛЯ ПРОВЕРКИ НАВЫКОВ РЕШЕНИЯ ЗАДАЧИ ПОИСКА ЦИКЛОВ ЭЙЛЕРА В ГРАФАХ</div><div id ="cont_help"><div class ="abz"><b>Элементы управления</b> - в данной виртуальной лаборатории вам доступны такие элементы управления и элементы взаимодействия с системой, как <b>вершины графа</b>, <b>ребра графа</b>, <b>кнопка отмены предыдущего щага</b>, <b>кнопка сброса всего решения</b>, и с помощью элемента <b>checkbox</b> вы можете пометить цикл как Эйлеров цикл</div><div class ="abz"><b>Вершины графа</b> - если навести курсор на вершину графа, виртуальна лаборатория покажет вам инцедентные ребра, если выбрать вершину графа, кликнув на нее, то ребро, соединяющее эти две вершины будет выделено и запишется в таблицу решения.</div><div class ="abz"><b>Обратите внимание</b> , начинать искать цикл Эйлера нужно с выделенной вершины, и все последующие циклы тоже должны начинаться с этой вершины.</div><div class ="abz"><b>Пометка цикла</b> -  если вы уверенны, что выделенный вами цикл - это цикл эйлера, то вы можете пометить его с помощью элемента управления checkbox, после этого со всех ребер снимется выделение и вы сможете продолжить поиск циклов Эйлера.</div><div class ="abz"><b>Отмена шага</b> — если вы хотит отменить последний выполненный вами шаг, нажмите на кнопку <b>Отмена</b>, обратите внимание, что если последним вашим шагом была пометка цикла, как цикл Эйлера, то выделение снимется.</div><div class ="abz"><b>Сброс решения</b> - если вы хотите сбросить всё решение, то нажмите на кнопку <b>Сброс</b>, обратите внимание, данная операция не может быть отменена. </div><div class ="abz"><b>В качестве решения</b> - от вас требуется выбрать все возможные циклы Эйлера, а так же указать их количество в специальном поле.</div> </div></div></div></div></div>',


}

window.onload = function() {
    Vlab.init();
};








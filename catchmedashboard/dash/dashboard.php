<!DOCTYPE html>

<?php
	error_reporting(0);
	require "conn.php";
	if( isset($_GET['b']) )
		$b = $_GET['b'];
	else 
		$b = "a";
	
	//total trips
	if($b == "a")
		$mysql_qry="SELECT IFNULL(COUNT(*),0) as tbus FROM bus_locations";
	else 
		$mysql_qry="SELECT IFNULL(COUNT(*),0) as tbus FROM bus_locations WHERE bus = ". $b;

	$sth = mysqli_query($conn, $mysql_qry);
	$tot_bus = 0;
	while($r = mysqli_fetch_assoc($sth))
		$tot_bus = $r['tbus'];
	
	//Average occupancy
	if($b == "a")
		$mysql_qry="SELECT IFNULL(SUM(t.quantity),0) as tt FROM tickets t";
	else 
		$mysql_qry="SELECT IFNULL(SUM(t.quantity),0) as tt FROM tickets t WHERE bus = ".$b;

	$sth = mysqli_query($conn, $mysql_qry);
	$tt = 0;
	while($r = mysqli_fetch_assoc($sth))
		$tt = $r['tt'];
	
	if($b == "a")
		$mysql_qry="SELECT IFNULL(SUM(seating_capacity),0) as sc FROM busses";
	else 
		$mysql_qry="SELECT IFNULL(SUM(seating_capacity),0) as sc FROM busses WHERE id = ".$b;

	$sth = mysqli_query($conn, $mysql_qry);
	$sc = 0;
	while($r = mysqli_fetch_assoc($sth))
		$sc = $r['sc'];
	
	$occupancy = round($tt/$sc * 100);
	
	//Total Revenue
	if($b == "a")
		$mysql_qry="SELECT IFNULL(SUM(amount),0) AS tr FROM tickets";
	else 
		$mysql_qry="SELECT IFNULL(SUM(amount),0) AS tr FROM tickets WHERE bus = ".$b;

	$sth = mysqli_query($conn, $mysql_qry);
	$tr = 0;
	while($r = mysqli_fetch_assoc($sth))
		$tr = $r['tr'];
	
	//Revenue Variation - Days
	$mysql_qry="SELECT DATE(tdate) as tdate FROM tickets ";
	if( $b != "a")
		$mysql_qry .= " WHERE bus = ".$b;

	$mysql_qry .= " GROUP BY DATE(tdate)";
	
	$sth = mysqli_query($conn, $mysql_qry);
	echo "<script> var days = []; </script>";
	while($r = mysqli_fetch_assoc($sth))
		echo "<script> days.push('".$r['tdate']."'); </script>";
	
	//Revenue Variation - Values
	$mysql_qry="SELECT IFNULL(SUM(amount),0) AS amount FROM tickets ";
	if( $b != "a")
		$mysql_qry .= " WHERE bus = ".$b;
	
	$mysql_qry .= " GROUP BY DATE(tdate)";

	$sth = mysqli_query($conn, $mysql_qry);
	echo "<script> var revenue = []; </script>";
	while($r = mysqli_fetch_assoc($sth))
		echo "<script> revenue.push(".$r['amount']."); </script>";
	
	//Hotspots
	$mysql_qry="SELECT p.name AS name, IFNULL(SUM(quantity),0) AS y
				FROM tickets t 
				LEFT JOIN places p ON p.id = t.origin";
	
	if( $b != "a")
		$mysql_qry .= " WHERE t.bus = ".$b;
	
	$mysql_qry .= " GROUP BY t.origin order by IFNULL(sum(quantity),0) desc limit 7 offset 0";

	$sth = mysqli_query($conn, $mysql_qry);
	echo "<script> var hotspots = []; </script>";
	while($r = mysqli_fetch_assoc($sth))
	{
		echo "<script>
					var hs = {};
					hs.name = '". $r['name']."';
					hs.y = ".$r['y'].";
					hotspots.push(hs);
			</script>";
	}
	
	//BusValue
	$mysql_qry="SELECT WEEKDAY(tdate) AS wday, HOUR(tdate) AS thour, p.name AS name, IFNULL(SUM(quantity),0) AS z
					FROM tickets t 
					LEFT JOIN places p ON p.id = t.origin";
	
	if( $b != "a")
		$mysql_qry .= " WHERE bus = ".$b;
	
	$mysql_qry .= " GROUP BY WEEKDAY(tdate),t.origin
					ORDER BY WEEKDAY(tdate)";

	$sth = mysqli_query($conn, $mysql_qry);
	echo "<script> var busvalues = []; </script>";
	while($r = mysqli_fetch_assoc($sth))
	{
		echo "<script>
					var hs = {};
					hs.name = '". $r['name']."';
					hs.country = '". $r['name']."';
					hs.x = ".$r['thour'].";
					hs.y = ".$r['wday'].";
					hs.z = ".$r['z'].";
					busvalues.push(hs);
			</script>";
	}
	
	echo "<script> console.log(busvalues); </script>"
?>

<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="robots" content="all,follow">
    <meta name="googlebot" content="index,follow,snippet,archive">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Catch Me</title>

    <meta name="keywords" content="">

    <link href='http://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,500,700,800' rel='stylesheet' type='text/css'>

    <!-- Bootstrap and Font Awesome css -->
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

    <!-- Css animations  -->
    <link href="css/animate.css" rel="stylesheet">

    <!-- Theme stylesheet, if possible do not edit this stylesheet -->
    <link href="css/style.default.css" rel="stylesheet" id="theme-stylesheet">

    <!-- Custom stylesheet - for your changes -->
    <link href="css/custom.css" rel="stylesheet">

    <!-- Custom POLA stylesheet - for your changes -->
    <link href="css/POLA-custom.css" rel="stylesheet">

    <!-- Responsivity for older IE -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

    <!-- Favicon and apple touch icons-->
    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
    <link rel="apple-touch-icon" href="img/apple-touch-icon.png" />
    <link rel="apple-touch-icon" sizes="57x57" href="img/apple-touch-icon-57x57.png" />
    <link rel="apple-touch-icon" sizes="72x72" href="img/apple-touch-icon-72x72.png" />
    <link rel="apple-touch-icon" sizes="76x76" href="img/apple-touch-icon-76x76.png" />
    <link rel="apple-touch-icon" sizes="114x114" href="img/apple-touch-icon-114x114.png" />
    <link rel="apple-touch-icon" sizes="120x120" href="img/apple-touch-icon-120x120.png" />
    <link rel="apple-touch-icon" sizes="144x144" href="img/apple-touch-icon-144x144.png" />
    <link rel="apple-touch-icon" sizes="152x152" href="img/apple-touch-icon-152x152.png" />
    <!-- owl carousel css -->

    <link href="css/owl.carousel.css" rel="stylesheet">
    <link href="css/owl.theme.css" rel="stylesheet">
	
	<style>
		body, html
		{
			overflow-x: hidden;
		}
	</style>
</head>

<body>

    <div id="all">

        <header>

            <!-- *** TOP ***
_________________________________________________________ -->
            <!-- <div id="top">
                <div class="container">
                    <div class="row">
                        <div class="col-xs-5 contact">
                            <p class="hidden-sm hidden-xs">Contact us on +420 777 555 333 or hello@universal.com.</p>
                            <p class="hidden-md hidden-lg"><a href="#" data-animate-hover="pulse"><i class="fa fa-phone"></i></a>  <a href="#" data-animate-hover="pulse"><i class="fa fa-envelope"></i></a>
                            </p>
                        </div>
                        <div class="col-xs-7">
                            <div class="social">
                                <a href="#" class="external facebook" data-animate-hover="pulse"><i class="fa fa-facebook"></i></a>
                                <a href="#" class="external gplus" data-animate-hover="pulse"><i class="fa fa-google-plus"></i></a>
                                <a href="#" class="external twitter" data-animate-hover="pulse"><i class="fa fa-twitter"></i></a>
                                <a href="#" class="email" data-animate-hover="pulse"><i class="fa fa-envelope"></i></a>
                            </div>

                            <div class="login">
                                <a href="#" data-toggle="modal" data-target="#login-modal"><i class="fa fa-sign-in"></i> <span class="hidden-xs text-uppercase">Sign in</span></a>
                                <a href="customer-register.html"><i class="fa fa-user"></i> <span class="hidden-xs text-uppercase">Sign up</span></a>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
 -->
            <!-- *** TOP END *** -->

            <!-- *** NAVBAR ***
    _________________________________________________________ -->

            <div class="navbar-affixed-top navbar-inner-page" data-spy="affix" data-offset-top="200">

                <div class="navbar navbar-default yamm navbar-inner" role="navigation" id="navbar" style="background:#ffff02">

                    <div class="container">
                        <div class="navbar-header">

                            <a class="navbar-brand" href="buyer.html">
                                <img src="images/catchME1.png" width="28" alt="Universal logo" class="hidden-xs hidden-sm">
                                <img src="images/catchME1.png" alt="Universal logo" class="visible-xs visible-sm"><span class="sr-only">Universal - go to homepage</span>
                            
                            </a>
                            <div class="navbar-buttons">
                                <button type="button" class="navbar-toggle btn-template-main" data-toggle="collapse" data-target="#navigation">
                                    <span class="sr-only">Toggle navigation</span>
                                    <i class="fa fa-align-justify"></i>
                                </button>
                            </div>
                        </div>
                        <!--/.navbar-header -->

                        <div class="navbar-collapse collapse" id="navigation">

                            <form class="navbar-form search" role="search">
                                    <div class="form-group col-sm-5">
                                        <input type="text" class="form-control" placeholder="Search for stats and data">
                                            <button type="submit" class="btn btn-search btn-template-main"><i class="fa fa-search"></i><span style="color:#fff;">|</span></button>
                                    </div>
                            </form>

                            <ul class="nav navbar-nav col-sm-5 navbar-right">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <div class="nav-icons">
                                            <div class="form-group col-sm-6">
                                                <img id="propic-thumb" src="images/propic-thumb.jpg" width="42" height="42">
                                                <a href="#" id="account" style="color:#000">Anoo Travels</a>
                                            </div>
                                            <div class="form-group col-sm-2">
                                                <a href="#" id="friendrq"></a><span class="new">2</span>
                                            </div>
                                            <div class="form-group col-sm-2">
                                                <a href="#" id="messages"></a><span class="new">4</span>
                                            </div>
                                            <div class="form-group col-sm-2">
                                                <a href="#" id="notifications"></a><span class="new">5</span>
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>
                                
                                <!-- <div class="col-sm-2">
                                    <div class="form-group">
                                        <input type="submit" class="form-control btn-login" value="Sign In" id="submit" />
                                    </div>
                                </div> -->
                            </ul>

                            

                        </div>
                        <!--/.nav-collapse -->



                        <div class="collapse clearfix" id="search">

                            <form class="navbar-form" role="search">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Search">
                                    <span class="input-group-btn">

                    <button type="submit" class="btn btn-template-main"><i class="fa fa-search"></i></button>

                </span>
                                </div>
                            </form>

                        </div>
                        <!--/.nav-collapse -->

                    </div>


                </div>
                <!-- /#navbar -->
                 <div class="navbar navbar-default yamm navbar-inner extended" role="navigation" id="navbar">
                    <ul class="nav navbar-nav col-sm-8 navbar-right">
                        <li class="dropdown use-yamm yamm-fw chats">
                            <a href="index.php">Home</a>
                        </li>
						<li class="dropdown use-yamm yamm-fw chats">
                            <a href="../signup.html">Subscription</a>
                        </li>
						<li class="dropdown use-yamm yamm-fw chats">
                            <a href="#">Extras</a>
                        </li>
						<li class="dropdown use-yamm yamm-fw chats">
                            <a href="../index.html">Logout</a>
                        </li>
						
                    </ul>
                 </div>

            </div>

            <!-- *** NAVBAR END *** -->

        </header>

        <!-- *** LOGIN MODAL ***
_________________________________________________________ -->

        <!-- <div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="Login" aria-hidden="true">
            <div class="modal-dialog modal-sm">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="Login">Customer login</h4>
                    </div>
                    <div class="modal-body">
                        <form action="customer-orders.html" method="post">
                            <div class="form-group">
                                <input type="text" class="form-control" id="email_modal" placeholder="email">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" id="password_modal" placeholder="password">
                            </div>

                            <p class="text-center">
                                <button class="btn btn-template-main"><i class="fa fa-sign-in"></i> Log in</button>
                            </p>

                        </form>

                        <p class="text-center text-muted">Not registered yet?</p>
                        <p class="text-center text-muted"><a href="customer-register.html"><strong>Register now</strong></a>! It is easy and done in 1&nbsp;minute and gives you access to special discounts and much more!</p>

                    </div>
                </div>
            </div>
        </div> -->

        <!-- *** LOGIN MODAL END *** -->

        <section>
            <!-- *** HOMEPAGE CAROUSEL ***
 _________________________________________________________ -->

            <div class="all">
                <div id="content">
                    <div class="container">
                        <div class="col-sm-2 newsfeed seller-account">
                            <div class="box">
                                <article class="post myaccount">
                                    <div class="heading seller-heading"><h4>My Buses</h4></div>
                                    <ul>
										<li <?php if($b=="a") echo 'class="active"'; ?>><a href="dashboard.php?b=a">All</a></li>
										<?php
											$mysql_qry="select id, `number` from busses limit 5 offset 0";

											$sth = mysqli_query($conn, $mysql_qry);
											while($r = mysqli_fetch_assoc($sth))
											{
												$c = "";
												if($b== $r['id']) $c = 'active';
												echo "<li class='".$c."'><a href='dashboard.php?b=".$r['id']."'>".$r['number']."</a></li>";
											}
										?>
	
                                    </ul>
                                </article>
                            </div>
                            <div class="box">
                                <article class="post myaccount">
                                    <div class="heading seller-heading"><h4>Feedback</h4></div>
                                    <ul class="feedback">
                                        <li class="feedback_bg"><img src="images/feedback.png" width="100%"></li>
                                        <li>240</li>
                                        <li>94</li>
                                        <li>32</li>
                                    </ul>
                                </article>
                            </div>
                        </div>

                        <div class="col-sm-6 street-box">
                            <div class="simple-box col-sm-12">
                                <ul class="breadcrumb">
                                    <li class="shops">Today - 09/10/2016</li>
                                </ul>
                                <div class="stat-summary col-sm-12">
                                    <div class="stat-box col-sm-3" style="padding:0 10px">
                                        <div>
                                        <div class="heading col-sm-12"><h4>Trips</h4></div>
                                        <div class="stat_details"><?php echo $tot_bus; ?></div>
                                        </div>
                                    </div>
                                    <div class="stat-box col-sm-5" style="padding:0 10px">
                                        <div>
                                        <div class="heading col-sm-12"><h4>Occupancy</h4></div>
                                        <div class="stat_details"><?php echo $occupancy; ?>%</div>
                                        </div>
                                    </div>
                                    <div class="stat-box col-sm-4" style="padding:0 10px">
                                        <div>
                                        <div class="heading col-sm-12"><h4>Revenue</h4></div>
                                        <div class="stat_details">Rs. <?php echo $tr; ?></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="simple-box col-sm-12">
                                <ul class="breadcrumb">
                                    <li class="shops">Overall Hotspots</li>
                                </ul>
                                <ul class="col-sm-3 pie-stats">
									<script>
										for(var x=0; x<hotspots.length; x++)
										{
											document.write("<li><b>" + hotspots[x].name + " - " + hotspots[x].y + "</b></li>")
										}
									</script>
                                </ul>
                                <div id="ordercontainer" class="col-sm-9" style="min-width: 310px; height:300px; float:right"></div>
                            </div>
							
							<div class="simple-box col-sm-12">
                                <ul class="breadcrumb">
                                    <li class="shops">Bus Value</li>
                                </ul>
                                <div id="vcontainer" class="col-sm-12" style="min-width: 310px; height:300px; float:right"></div>
                            </div>
                            
                        </div>
                        <div class="col-sm-4 street-box">
                            <div class="simple-box col-sm-12">
                                <ul class="breadcrumb">
                                    <li class="shops">Revenue Variation</li>
                                </ul>
                                <div id="container" class="col-sm-12" style="min-width: 310px; height:300px; margin: 0 auto;"></div>
                            </div>
                            <div class="col-sm-12 simple-box" style="display:none">
                                <div class="col-sm-12">
                                    <ul class="breadcrumb">
                                        <li class="shops">Products</li>
                                    </ul>
                                <div class="stat-box">
                                    <ul class="col-sm-12 product-stats">
                                        <li><a href="#">All</a><span>100</span></li>
                                        <li><a href="#">Live</a><span>86</span></li>
                                        <li><a href="#">Offline</a><span>14</span></li>
                                    </ul>
                                </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                              
            <!-- *** HOMEPAGE CAROUSEL END *** -->
        </section>

       
        <!-- /.bar -->

    </div>
    <!-- /#all -->


    <!-- #### JAVASCRIPT FILES ### -->

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script>
        window.jQuery || document.write('<script src="js/jquery-1.11.0.min.js"><\/script>')
    </script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

    <script src="js/jquery.cookie.js"></script>
    <script src="js/waypoints.min.js"></script>
    <script src="js/jquery.counterup.min.js"></script>
    <script src="js/jquery.parallax-1.1.3.js"></script>
    <script src="js/front.js"></script>

    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/highcharts-more.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <script>
		$(function () {
			$('#container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Total Revenue by Date'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: days,
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Revenue - LKR'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: 'Revenue',
            data: revenue

        }]
    });


			$('#ordercontainer').highcharts({
				chart: {
					plotBackgroundColor: null,
					plotBorderWidth: null,
					plotShadow: false,
					type: 'pie'
				},
				title: {
					text: ''
				},
				tooltip: {
					pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions: {
					pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						dataLabels: {
							enabled: false,
							format: '<b>{point.name}</b>: {point.percentage:.1f} %',
							style: {
								color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
							}
						}
					}
				},
				series: [{
					name: '',
					colorByPoint: true,
					data: hotspots
				}]
			});
		});
		
		
		$('#vcontainer').highcharts({

        chart: {
            type: 'bubble',
            plotBorderWidth: 1,
            zoomType: 'xy'
        },

        legend: {
            enabled: false
        },

        title: {
            text: 'Bus Value by Week Day and Hour'
        },

        subtitle: {
            text: ''
        },

        xAxis: {
            gridLineWidth: 1,
            title: {
                text: 'Hour of the Day'
            },
            labels: {
                format: '{value}'
            },
            plotLines: [{
                color: 'black',
                dashStyle: 'dot',
                width: 2,
                value: 65,
                label: {
                    rotation: 0,
                    y: 15,
                    style: {
                        fontStyle: 'italic'
                    },
                    text: 'Safe fat intake 65g/day'
                },
                zIndex: 3
            }]
        },

        yAxis: {
            startOnTick: false,
            endOnTick: false,
            title: {
                text: 'Week Day - 1: Sunday'
            },
            labels: {
                format: '{value}'
            },
            maxPadding: 0.2
        },

        tooltip: {
            useHTML: true,
            headerFormat: '<table>',
            pointFormat: '<tr><th colspan="2"><h3>{point.country}</h3></th></tr>' +
                '<tr><th>Hour: </th><td>{point.x}</td></tr>' +
                '<tr><th>Day:</th><td>{point.y}</td></tr>' +
                '<tr><th>Value:</th><td>{point.z}</td></tr>',
            footerFormat: '</table>',
            followPointer: true
        },

        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },

        series: [{
            data: busvalues
        }]

    });
	</script>

    

    <!-- owl carousel -->
    <script src="js/owl.carousel.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
        $('#chat-head .showall').hide();
        $('.chat-ind').hide();

        $('#chat-head .showless a').click(function(){
            $('#chat-head .showall').toggle();
        });

        $('#chat-head .showall a').click(function(){
            var str = $(this).html();
            $('.chat-ind').show();
            $('.chat-ind .heading a.chat-person').append(str);
        });
        $('.chat-ind a.close').click(function(){
            console.log("close");
            $(this).parents('.chat-ind').toggle();
        });

        $('.chat-type').submit(function(){
            var str = $(this).children('.text').val();
            if(str!=""){
                $(this).prev('.chat-body').append("<p class='col-sm-12'><span>"+str+"</span></p>");
                str="";
                $(this).children('.text').val(str);
            }
        });
    });
    </script>

</body>

</html>
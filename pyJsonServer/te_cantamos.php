<html>
<header>
	<title>Te cantamos</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.css"/>
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/1.11.3/js/dataTables.bulma.min.js"></script>
	<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.9.1/css/bulma.min.css"/>
	<link ref="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.3/css/dataTables.bulma.min.css"/>
</header>

<body style="margin:20px; padding:20px;">
<h1 class="title">Te cantamos</h1>
<p>Se muestran a lo sumo los últimos 256 registros.</p><br/><br/>
<?php 
	$servername = "localhost";
	$username = "mama";
	$password = "chorlito";
	$db = "mama_te_canta";

	$conn = new mysqli($servername,$username,$password,$db);

	if ($conn->connect_error) {
		die("Connection failed: ".$conn->connect_error);
	}

	#echo "Connected successfully<br/>";

	if ($result = $conn->query("SELECT * FROM log ORDER BY fecha DESC LIMIT 256")) {
		#echo "Number of rows: ".$result->num_rows."<br/>";
	} else {
		echo "Error en la consulta: ".$conn->error."<br/>";
	}
?>

	<table id="resultados">
		<thead>
			<tr>
				<th>Usuario</th>
				<th>Fecha</th>
				<th>Canción</th>
				<th>Inicio</th>
				<th>Fin</th>
			</tr>
		</thead>
		<tbody>
<?php
	while ($row = $result->fetch_assoc()) {
		$fecha=date("Y-m-d H:i:s",(int)substr($row["fecha"],0,-3));
		echo "<tr><td>".$row["usuario"]."</td><td>".$fecha."</td><td>",$row["nombre_cancion"]."</td><td>".$row["inicio"]."</td><td>",$row["fin"]."</td>";
	}
?>
		</tbody>
	</table>

<?php
	$conn->close();
	#phpinfo(); 
?>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#resultados').DataTable();
		} );
	</script>

</body>
</html>

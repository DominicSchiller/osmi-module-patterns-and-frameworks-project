/**
 * @author Dominic Schiller
 * @since 19.09.2017
 *
 * This is the server's starting point.
 */

// imports
let http = require('http');
let express = require('express');
let app = express();
let server = http.createServer(app);
//let io =
require('socket.io')(server);

// route configurations
app.use(express.static(__dirname + '/server/views/'));
app.get('/', function(req, res) {
   res.sendFile(__dirname, + '/index.html');
});

server.listen(5656, function() {
    console.info("ScrabbleFactory server is listening on port http://localhost:5656");
});

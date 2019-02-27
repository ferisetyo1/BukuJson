const express = require('express'),
    bodyParser = require('body-parser'),
    mysql = require('mysql'),
    app = express(),
    port = process.env.PORT || 3000;

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'nodejs_buku'
});

db.connect((err)=>{
    if(err) throw err;
    console.log("MySql Connected...");
});

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.listen(port,()=>{
    console.log('todo list RESTful API server started on: ' + port);
});

app.get('/book',(req,res) => {
    let sql = "SELECT judul,penulis,tahun,halaman,negara,bahasa,nama as kategori FROM list_buku a JOIN kategori b on a.kategori_id=b.id";
    let query = db.query(sql, (err, result,field)=>{
        if(err)throw err;
        res.end(JSON.stringify(result));
    })
});

app.get('/kategori',(req,res) => {
    let sql = "SELECT * FROM kategori";
    let query = db.query(sql, (err, result,field)=>{
        if(err)throw err;
        res.end(JSON.stringify(result));
    })
});
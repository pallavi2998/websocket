
const socketUrl="/stomp-endpoint";
const destination = "/topic/greetings";


let client = null
let socket = new SockJS(socketUrl)
client = Stomp.over(socket)
client.connect({},function (){
 console.log("Connected!!")
    client.subscribe(destination,function (data){
        print(JSON.parse(data.body))
        console.log(data)
    })
})

function  print(message){
    let log = document.getElementById("logs")
    log.innerHTML += "<tr><td>" + message.content + "</td></tr><br>";

}



window.onload = function(){

  // var http = new XMLHttpRequest();
  //
  // http.onreadystatechange = function(){
  //   if(http.readyState ==4 && http.status ==200){
  //     console.log(JSON.parse(http.response));
  //   }
  // }

  //http.open("GET", "http://127.0.0.1:8080/home",true);


  $.get("http://127.0.0.1:8080/home", function(data){
    var array;

      array = data["post1"];
      var post1 = document.getElementById("post1");
      for(var i=0;i<array.length;i++){
        var t = document.createTextNode(array[i]);
        var br = document.createElement("br");
        post1.appendChild(t);
        post1.appendChild(br);
      }

      array = data["post2"];
      var post2 = document.getElementById("post2");
      for(var i=0;i<array.length;i++){
        var t = document.createTextNode(array[i]);
        var br = document.createElement("br");
        post2.appendChild(t);
        post2.appendChild(br);
      }

      array = data["post3"];
      var post3 = document.getElementById("post3");
      for(var i=0;i<array.length;i++){
        var t = document.createTextNode(array[i]);
        var br = document.createElement("br");
        post3.appendChild(t);
        post3.appendChild(br);
      }

      array = data["post4"];
      var post4 = document.getElementById("post4");
      for(var i=0;i<array.length;i++){
        var t = document.createTextNode(array[i]);
        var br = document.createElement("br");
        post4.appendChild(t);
        post4.appendChild(br);
      }

      array = data["post5"];
      var post5 = document.getElementById("post5");
      for(var i=0;i<array.length;i++){
        var t = document.createTextNode(array[i]);
        var br = document.createElement("br");
        post5.appendChild(t);
        post5.appendChild(br);
      }

      array = data["post6"];
      var post6 = document.getElementById("post6");
      for(var i=0;i<array.length;i++){
        var t = document.createTextNode(array[i]);
        var br = document.createElement("br");
        post6.appendChild(t);
        post6.appendChild(br);
      }

      array = data["post7"];
      var post7 = document.getElementById("post7");
      for(var i=0;i<array.length;i++){
        var t = document.createTextNode(array[i]);
        var br = document.createElement("br");
        post7.appendChild(t);
        post7.appendChild(br);
      }


      array = data["post8"];
      var post8 = document.getElementById("post8");
      for(var i=0;i<array.length;i++){
        var t = document.createTextNode(array[i]);
        var br = document.createElement("br");
        post8.appendChild(t);
        post8.appendChild(br);
      }

      array = data["post9"];
      var post8 = document.getElementById("post9");
      for(var i=0;i<array.length;i++){
        var t = document.createTextNode(array[i]);
        var br = document.createElement("br");
        post9.appendChild(t);
        post9.appendChild(br);
      }

      array = data["post10"];
      var post8 = document.getElementById("post10");
      for(var i=0;i<array.length;i++){
        var t = document.createTextNode(array[i]);
        var br = document.createElement("br");
        post10.appendChild(t);
        post10.appendChild(br);
      }


  })


}

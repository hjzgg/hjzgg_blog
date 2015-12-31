function changeBkImg(){
	var lipan = Math.random(); 
	lipan = lipan * 3;            
	lipan = Math.round(lipan);     
	var lipath = "";
	switch(lipan){
		  case 0: lipath = "./image/huaqiangu1.jpg";break;  
		  case 1: lipath = "./image/huaqiangu2.jpg";break;
		  case 2: lipath = "./image/huaqiangu3.jpg";break;
		  case 3: lipath = "./image/huaqiangu4.jpg";break;
	}
	document.body.style.backgroundImage = "URL("+lipath+")"; 
}
setInterval("changeBkImg()", 8000); 
 
	var hjzgg_rector
	var hjzgg_a
	var hjzgg_shake
	function hjzgg_init(which){
		hjzgg_rector=5
		hjzgg_a=1
		hjzgg_shake=which
		hjzgg_rattleimage()
	}
	function hjzgg_rattleimage(){
		if (hjzgg_a==1){
			hjzgg_shake.style.top=parseInt(hjzgg_shake.style.top)+hjzgg_rector+"px"
		}
		else if (hjzgg_a==2){
			hjzgg_shake.style.left=parseInt(hjzgg_shake.style.left)+hjzgg_rector+"px"
		}
		else if (hjzgg_a==3){
			hjzgg_shake.style.top=parseInt(hjzgg_shake.style.top)-hjzgg_rector+"px"
		}
		else{
			hjzgg_shake.style.left=parseInt(hjzgg_shake.style.left)-hjzgg_rector+"px"
		}
		if (hjzgg_a<4)
			hjzgg_a++
		else
			hjzgg_a=1
		setTimeout("hjzgg_rattleimage()", 200)
	}
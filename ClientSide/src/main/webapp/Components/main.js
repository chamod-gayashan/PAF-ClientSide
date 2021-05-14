$(document).ready(function()
{ 
 	if ($("#alertSuccess").text().trim() == "") 
 	{ 
 		$("#alertSuccess").hide(); 
 	} 
 	$("#alertError").hide(); 
 
}); 

// SAVE BUTTON ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear status msges-------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation----------------
	var status = validateItemForm();
	// If not valid-------------------
	
	if (status != true)
	{	
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "ItemsAPI",
		type : type,
		data : $("#formItem").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onItemSaveComplete(response.responseText, status);
		}
	});
});

//form fill (IF CHECK UPDATE BUTTON FILL FIELD DATA)
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidItemIDSave").val($(this).data("itemid"));
	//$("#itemCode").val($(this).closest("tr").find('td:eq(0)').text());
	$("#itemName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#itemPrice").val($(this).closest("tr").find('td:eq(1)').text());
	$("#itemDesc").val($(this).closest("tr").find('td:eq(2)').text());
});


//Save Command Successfull msg show
function onItemSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		
	} else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}




// CLIENT-MODEL================================================================
function validateItemForm()
{
	/*
	// CODE
	if ($("#itemCode").val().trim() == "")
	{
		return "Insert Item Code.";
	}
	*/
	
	// NAME
	if ($("#itemName").val().trim() == "")
	{
		return "Insert Item Name.";
	}
	
	// PRICE-------------------------------
	if ($("#itemPrice").val().trim() == "")
	{
		return "Insert Item Price.";
	}
	
	// is numerical value
	var tmpPrice = $("#itemPrice").val().trim();
	if (!$.isNumeric(tmpPrice))
	{
		return "Insert a numerical value for Project  Price.";
	}
	
	// convert to decimal price
	$("#itemPrice").val(parseFloat(tmpPrice).toFixed(2));
	
	// DESCRIPTION------------------------
	if ($("#itemDesc").val().trim() == "")
	{
		return "Insert Item Description.";
	}
return true;
}


//Delete Button 
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
		url : "ItemsAPI",
		type : "DELETE",
		data : "projectID=" + $(this).data("itemid"),
		dataType : "text",
		complete : function(response, status)
		{
			onItemDeleteComplete(response.responseText, status);
		}
	});
});

//Delete Button Operation Messages 
function onItemDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
			if (resultSet.status.trim() == "success")
			{
				$("#alertSuccess").text("Successfully deleted.");
				$("#alertSuccess").show();
				//reload html table
				$("#divItemsGrid").html(resultSet.data);
			} else if (resultSet.status.trim() == "error")
				
			{
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
			}
	
	} else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}



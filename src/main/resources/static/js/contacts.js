const viewContactModal = document.getElementById("view_contact_model")
console.log("hello")
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'modalEl',
  override: true
};

const contactmodel = new Modal(viewContactModal,options,instanceOptions)

function openContactModel(){
    contactmodel.show();
}


function closeContactModel(){
    contactmodel.hide();
}


async function loadContactData(id){

    console.log(id)
   try{
    const data =await (await fetch(`http://localhost:9090/api/contacts/${id}`)).json()
    document.getElementById("contact_name").innerHTML=data.name;
    document.getElementById("contact_email").innerHTML=data.email;
    document.getElementById("address").innerHTML = data.address;
    document.getElementById("description").innerHTML=data.description;
    contactmodel.show()


   }
   catch(error){
    

   }

    



}



//delete contact

async function deletecontact(id) {

    const url="http://localhost:9090/user/contacts/delete/"+id;
    Swal.fire({
        title: "Do you want to delete this contact?",
        icon:"warning",
        showCancelButton: true,
        confirmButtonText: "Delete",
       
      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            
            window.location.replace(url)
          Swal.fire("Deleted!", "", "success");
        } 
      });
 
    
}
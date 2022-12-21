        function addFields() {


            var arrayType = ["Maj","Moll","Dominant7","Maj7","Maj"];

            // Generate a dynamic number of inputs
            var number = document.getElementById("member").value;
            // Get the element where the inputs will be added to
            var container = document.getElementById("form");
            // Remove every children it had before
             while (container.hasChildNodes()) {
                 container.removeChild(container.lastChild);
            }
            for (i=0;i<number;i++){
                // Append a node with a random text
                container.appendChild(document.createTextNode("Chord " + (i+1)+" "));
                // Create an <input> element, set its type and name attributes
                var singleNote = document.createElement("input");
                singleNote.type = "text";
                singleNote.name = "singleNote";
                singleNote.id="singleNote"+i
                singleNote.placeholder="Single Note"
                container.appendChild(singleNote);


//                var selectList = document.createElement("select");
//                selectList.id ="type"+i
//                for (var i = 0; i < arrayType.length; i++) {
//                option = document.createElement("option");
//                option.value = arrayType[i];
//                option.text = arrayType[i];
//                selectList.appendChild(option);
//                }
//                container.appendChild(selectList);


                var type = document.createElement("input");
                type.type = "text";
                type.name = "type";
                type.id="type"+i
                type.placeholder="Type"
                container.appendChild(type);

                var length = document.createElement("input");
                length.type = "number";
                length.name = "length";
                length.id="length"+i
                length.placeholder="Length"
                container.appendChild(length);

                var complexity = document.createElement("input");
                complexity.type = "number";
                complexity.name = "complexity";
                complexity.id="complexity"+i
                complexity.placeholder="Complexity"
                container.appendChild(complexity);

                // Append a line break
                container.appendChild(document.createElement("br"));
//
//                 var button = document.createElement('button');
//                 button.innerHTML = 'click me';
//                 button.onclick = sendForm();
////                 button.addEventListener("click", sendForm);
//                 container.appendChild(button);

                if (number>0) {
                let element = document.getElementById("senddetails");
                element.removeAttribute("hidden");
                }



            }

        }
//        )
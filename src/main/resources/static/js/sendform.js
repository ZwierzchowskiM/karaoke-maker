  function sendForm() {

        var songname = document.getElementById("songnameid").value
        const chords = getData();
        const obj = {songname,chords};

        fetch("/song/compose/generate", {
            method: 'POST',
            body:JSON.stringify(obj),
            headers: {
                    "Content-Type": "application/json;charset=UTF-8"
            }
        })
            .then((response) => {
               return response
            })
            .then((data) => {
                console.log(data)
            })

}

 function getData() {


            chord_data = [];
            var number = document.getElementById("member").value


            for (let index = 0; index < number; index++) {
                item = {};

                var singleNote ='singleNote'
                var type = 'type'
                var length ='length'
                var complexity = 'complexity'
                item[singleNote]  = document.getElementById('singleNote'+index).value;
                item[type]  = document.getElementById('type'+index).value;
                item[length]  = document.getElementById('length'+index).value;
                item[complexity]  = document.getElementById('complexity'+index).value;

                chord_data.push(item)
            }


            return chord_data

        }

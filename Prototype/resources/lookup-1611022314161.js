(function(window, undefined) {
  var dictionary = {
    "27c20d47-b576-4ecd-87f1-184461630cbe": "Login",
    "f44958cd-ac11-425e-ac3f-edbe8f39a350": "Change password",
    "ea8425a5-559b-4b05-bb41-58d7b31e45a0": "Categories",
    "733f99a4-8f8d-4a9c-9a75-1d1c435ca03c": "Register",
    "9de4894e-cbf5-497d-8ca1-462b3116b4ef": "Slider3",
    "9865a60e-2273-4bdf-b615-dab4b3fb08d8": "SplashScreen",
    "d12245cc-1680-458d-89dd-4f0d7fb22724": "Slider1",
    "fb8cc2f7-147a-4844-9247-2483c2cbe4e8": "Slider2",
    "f39803f7-df02-4169-93eb-7547fb8c961a": "Template 1",
    "bb8abf58-f55e-472d-af05-a7d1bb0cc014": "default"
  };

  var uriRE = /^(\/#)?(screens|templates|masters|scenarios)\/(.*)(\.html)?/;
  window.lookUpURL = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, url;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      url = folder + "/" + canvas;
    }
    return url;
  };

  window.lookUpName = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, canvasName;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      canvasName = dictionary[canvas];
    }
    return canvasName;
  };
})(window);
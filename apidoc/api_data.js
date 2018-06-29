define({ "api": [
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "./apidoc/main.js",
    "group": "C__Users_N4050_eclipse_workspace_plakaYorum_apidoc_main_js",
    "groupTitle": "C__Users_N4050_eclipse_workspace_plakaYorum_apidoc_main_js",
    "name": ""
  },
  {
    "type": "get",
    "url": "/plaka/:plate",
    "title": "Aranan plakanın tüm yorumları",
    "version": "0.1.0",
    "name": "GetPlateComments",
    "group": "Plate",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "plate",
            "description": "<p>Plaka</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "comments",
            "description": "<p>Yorumlar.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n[\n  \"comment 1\" ,\n  \"comment 2\" \n]",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"error\": \"Plate is not suitable\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./src/main/java/com/yorum/plaka/resources/PlateCommentResource.java",
    "groupTitle": "Plate"
  },
  {
    "type": "get",
    "url": "/plaka",
    "title": "Tüm plakalar",
    "version": "0.1.0",
    "name": "GetPlates",
    "group": "Plate",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>Plaka Id.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "plate",
            "description": "<p>Plaka.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "  HTTP/1.1 200 OK\n[  \n  {\n    \"id\": \"1\",\n    \"plate\": \"34AS772\"\n  },\n  {\n    \"id\": \"2\",\n    \"plate\": \"57AS277\"\n  }\n]",
          "type": "json"
        }
      ]
    },
    "filename": "./src/main/java/com/yorum/plaka/resources/PlateCommentResource.java",
    "groupTitle": "Plate"
  },
  {
    "type": "post",
    "url": "/plaka/:plate",
    "title": "Plaka ve yorum kaydet",
    "version": "0.1.0",
    "name": "SavePlateComments",
    "group": "Plate",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "plate",
            "description": "<p>Plaka</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "comment",
            "description": "<p>Yorum</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created.",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"error\": \"Plate is not suitable or incomplete request\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "./src/main/java/com/yorum/plaka/resources/PlateCommentResource.java",
    "groupTitle": "Plate"
  }
] });

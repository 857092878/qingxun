/**
 * 给出table的名称，返回pascal和camel名称
 */


/**
 * 返回camel名称
 */
export function getCamelName(tableName) {
  let newName = "";
  let sNow = tableName;
  let ind = sNow.indexOf("_");
  while (ind >= 0) {
    newName = newName + sNow.substr(0, ind);
    sNow = sNow.substring(ind + 1);
    if(sNow.length>0){
      sNow=sNow.substr(0,1).toUpperCase()+sNow.substring(1);
    }
    ind = sNow.indexOf("_");
  }
  newName = newName + sNow;
  return newName;
}

/**
 * 返回pascal名称
 */
export function getPascalName(camelName) {
 let pascalName=camelName;
 if(pascalName.length>0){
   pascalName=pascalName.substr(0,1).toUpperCase()+pascalName.substring(1);
 }
 return pascalName;
}


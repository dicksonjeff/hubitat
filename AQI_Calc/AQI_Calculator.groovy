

definition(
    name: "AQI Calculator",
    namespace: "DicksonJ",
    author: "Jeff Dickson",
    description: "This Application will read PM25 sensor from users device and calculates USA AQI",
    category: "Air Quality",
    iconUrl: "",
    iconX2Url: "")

	

preferences {
	
    page(name: "mainPage") 
	getGlobalVar("haqi")
	getGlobalVar("haqi_index")
	//this.addInUseGlobalVar(String aqiColor)
}
def mainPage(){
	dynamicPage(name: "mainPage", title: "Calculate AQI", nextPage: null, install: true, uninstall: true, refreshinterval: 0){
		section("Select your device"){
			//get device from user
			input "chosenDevice", "capability.polling", title: "Choose your Sensoring Device", multiple: false, required: true, submitOnChange:true

			
		}
		section("Display Attributes"){
			//get attributes available from device and make a list
			
			if(chosenDevice != null ){
			//String strAttributes = chosenDevice?.supportedAttributes
			//def attributeList = strAttributes.substring(1, strAttributes.size() -1).split(",").collect{it as String}
			def attributeList = chosenDevice?.supportedAttributes.collect {it.name}
				if(attributeList != null){
					// grab attribute from user
					input (name: "selectedAttribute", title: "Select PM25 Sensor Attribute", type: "enum", options: attributeList.sort(), submitOnChange:true, width:3, defaultValue: 1)
					
					
					// Run Calculation method
					if(selectedAttribute != null){

						Double value = chosenDevice.currentValue("${selectedAttribute}") as Double
						calculateAQI(value)
		

					}
				}
			
			}

			
		}
		section("Logging"){
			input name: "logEnable", type: "bool", title: "Enable logging?"
		}
		

	}
}


def calculateAQI(pm25value){
// Will calculate AQI value for USA AQI
aqiHi = 0
aqiLow = 0
concHi = 0
concLow = 0
concI = 0
if (logEnable) log.debug "pm25 value is ${pm25value}"

}

	//Good Calculation
	if (pm25value <= 12.0){
		concHi = 12
		concLow = 0
		aqiHi = 50
		aqiLow = 0
		concI = pm25value
		
		if (logEnable) log.debug "im in Green"

		aqiIndex = "Good"
		aqiColor = "Green"
		aqiValue = ((aqiHi - aqiLow) / (concHi - concLow) * (concI-concLow) + aqiLow)
		aqiValue = ((int) aqiValue * 100)/100
		if (logEnable) log.debug "Air Quality is ${aqiIndex} and ${aqiColor} and the Index Value is ${aqiValue}"
		setGlobalVar("haqi", "The AQI Index is ${aqiValue}, the color is ${aqiColor} and the Quality is ${aqiIndex}")
		setGlobalVar("haqi_index", "${aqiValue}")
		
	}

	//Moderate Calculation
	else if (pm25value >= 12.1  && pm25value <= 35.4 ){
		concHi = 35.4
		concLow = 12.1
		aqiHi = 100
		aqiLow = 51
		concI = pm25value

		if (logEnable) log.debug "im in Yellow"

		aqiIndex = "Moderate"
		aqiColor = "Yellow"
		
		aqiValue = ((aqiHi - aqiLow) / (concHi - concLow) * (concI-concLow) + aqiLow)
		aqiValue = ((int) aqiValue * 100)/100
		if (logEnable) log.debug "Air Quality is ${aqiIndex} and ${aqiColor} and the Index Value is ${aqiValue}"
		setGlobalVar("haqi", "The AQI Index is ${aqiValue}, the color is ${aqiColor} and the Quality is ${aqiIndex}")
		setGlobalVar("haqi_index", "${aqiValue}")
	}
	//Unhealthy for Sensitive Groups
	else if(pm25value >= 35.5 && pm25value <= 55.4){
		concHi = 55.4
		concLow = 35.5
		aqiHi = 150
		aqiLow = 101
		concI = pm25value

		if (logEnable) log.debug "im in Orange"

		aqiIndex = "Unhealthy for Sensitive Groups"
		aqiColor = "Orange"
		
		aqiValue = ((aqiHi - aqiLow) / (concHi - concLow) * (concI-concLow) + aqiLow)
		aqiValue = ((int) aqiValue * 100)/100
		if (logEnable) log.debug "Air Quality is ${aqiIndex} and ${aqiColor} and the Index Value is ${aqiValue}"
		setGlobalVar("haqi", "The AQI Index is ${aqiValue}, the color is ${aqiColor} and the Quality is ${aqiIndex}")
		setGlobalVar("haqi_index", "${aqiValue}")
	}
	// Unhealthy
	else if (pm25value >= 55.5 && pm25value <= 150.4){
		concHi = 150.4
		concLow = 55.5
		aqiHi = 200
		aqiLow = 151
		concI = pm25value
		if (logEnable) log.debug "im in Red"

		aqiIndex = "Unhealthy"
		aqiColor = "Red"
		
		aqiValue = ((aqiHi - aqiLow) / (concHi - concLow) * (concI-concLow) + aqiLow)
		aqiValue = ((int) aqiValue * 100)/100
		if (logEnable) log.debug "Air Quality is ${aqiIndex} and ${aqiColor} and the Index Value is ${aqiValue}"
		setGlobalVar("haqi", "The AQI Index is ${aqiValue}, the color is ${aqiColor} and the Quality is ${aqiIndex}")
		setGlobalVar("haqi_index", "${aqiValue}")
	}
	//Very Unhealthy
	else if (pm25value >= 150.5 && pm25value <= 250.4){
		concHi = 250.4
		concLow = 150.5
		aqiHi = 300
		aqiLow = 201
		concI = pm25value

		if (logEnable) log.debug "im in Purple"

		aqiIndex = "Very Unhealthy"
		aqiColor = "Purple"
		
		aqiValue = ((aqiHi - aqiLow) / (concHi - concLow) * (concI-concLow) + aqiLow)
		aqiValue = ((int) aqiValue * 100)/100
		if (logEnable) log.debug  "Air Quality is ${aqiIndex} and ${aqiColor} and the Index Value is ${aqiValue}"
		setGlobalVar("haqi", "The AQI Index is ${aqiValue}, the color is ${aqiColor} and the Quality is ${aqiIndex}")
		setGlobalVar("haqi_index", "${aqiValue}")
	}//Hazardous
	else (pm25value >= 250.5 && pm25value<= 500.4){
		concHi = 500.4
		concLow = 250.5
		aqiHi = 500
		aqiLow = 301
		concI = pm25value

		if (logEnable) log.debug "im in Maroon"

		aqiIndex = "Hazardous"
		aqiColor = "Maroon"
		
		aqiValue = ((aqiHi - aqiLow) / (concHi - concLow) * (concI-concLow) + aqiLow)
		aqiValue = ((int) aqiValue * 100)/100
		if (logEnable) log.debug  "Air Quality is ${aqiIndex} and ${aqiColor} and the Index Value is ${aqiValue}"
		setGlobalVar("haqi", "The AQI Index is ${aqiValue}, the color is ${aqiColor} and the Quality is ${aqiIndex}")
		setGlobalVar("haqi_index", "${aqiValue}")
	}

}


def installed() {
	log.debug "Installed Application"
	updated()
}

def updated(){
	if (logEnable) log.debug "Updated Application"

	unsubscribe()
	initialize()
}

def initialize(){
	log.info "Initializing with settings: ${settings}"
	//subscribeNow()
	subscribe()

	//runEvery1Minute(refresh)
}
def subscribe(){
	//Awair element
	subscribe(settings.chosenDevice,"pm25", "eventHandler")
	//AirNow
	subscribe(settings.chosenDevice,"Pm25", "eventHandler")
	if (logEnable) log.info "Polled Data from Driver"
}

def eventHandler(evt){
	if (logEnable) log.debug "Device was polled PM25: ${evt.value}"
	//re-run aqi calcs
	Double newval = evt.value as Double
	calculateAQI(newval)	
}



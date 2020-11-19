require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "plotprojects-react-native-module"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  plotprojects-react-native-module
                   DESC
  s.homepage     = "https://www.plotprojects.com/"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "PlotProjects" => "support@plotprojects.com" }
  s.platforms    = { :ios => "13.0" }
  s.source       = { :git => "https://github.com/Plotprojects/plotprojects-react-native-module.git", :tag => "1.0.0" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency "PlotPlugin"
  # ...
  # s.dependency "..."
end


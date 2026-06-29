import AppRoutes from "./routes/AppRoutes";
import { LayoutProvider } from "./context/LayoutContext";

const App = () => {
    return (
        <LayoutProvider>
            <AppRoutes />
        </LayoutProvider>
    );
};

export default App;
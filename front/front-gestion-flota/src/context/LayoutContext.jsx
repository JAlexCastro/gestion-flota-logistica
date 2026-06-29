import { createContext, useContext, useState } from "react";

const LayoutContext = createContext();

export const useLayout = () => useContext(LayoutContext);

export function LayoutProvider({ children }) {

    const [sidebarOpen, setSidebarOpen] = useState(true);

    const toggleSidebar = () => {
        setSidebarOpen(prev => !prev);
    };

    return (
        <LayoutContext.Provider value={{
            sidebarOpen,
            toggleSidebar
        }}>
            {children}
        </LayoutContext.Provider>
    );
}